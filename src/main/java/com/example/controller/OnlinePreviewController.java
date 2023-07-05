package com.example.controller;

import com.example.common.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/onlinePreview")
public class OnlinePreviewController {

    @GetMapping("/text")
    public R previewText(String absolutePath) throws IOException {
        File file = new File(absolutePath);
        if (!file.exists()) return R.error("预览文件不存在");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
            return R.success(sb);
        }
    }

    @GetMapping("/image")
    public void previewImage(String absolutePath, HttpServletResponse response) throws IOException {
        File file = new File(absolutePath);
        if (!file.exists()) return;

        response.setContentType("application/octet-stream");

        try (
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            IOUtils.copy(inputStream,outputStream);
        }
    }

    @GetMapping("/video")
    public void previewVideo(String absolutePath, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.reset();
        File file = new File(absolutePath);
        long fileLength = file.length();
        // 随机读文件
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

        //获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");
        long range = 0;
        if (Objects.nonNull(rangeString)) {
            System.out.println(rangeString);
            range = Long.parseLong(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
        }
        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        //设置内容类型
        response.setHeader("Content-Type", "video/mp4");
        //返回码需要为206，代表只处理了部分请求，响应了部分数据
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        // 移动访问指针到指定位置
        randomAccessFile.seek(range);
        // 每次请求只返回1MB的视频流
        byte[] bytes = new byte[1024 * 1024];
        int len = randomAccessFile.read(bytes);
        //设置此次相应返回的数据长度
        response.setContentLength(len);
        //设置此次相应返回的数据范围
        response.setHeader("Content-Range", "bytes " + range + "-" + (Math.min((range + 1024 * 1024), fileLength-1)) + "/" + fileLength);
        // 将这1MB的视频流响应给客户端
        outputStream.write(bytes, 0, len);
        outputStream.close();
        randomAccessFile.close();

        System.out.println("返回数据区间:【" + range + "-" + (range + len) + "】");
    }

}
