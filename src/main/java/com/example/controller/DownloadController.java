package com.example.controller;

import com.example.util.Utils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

/**
 * 下载文件
 */
@RestController
@RequestMapping("/download")
@Slf4j
public class DownloadController {

    @Value("${project-file-path.download}")
    private String downloadPath;

    @Value("${project-file-path.temporary}")
    private String temporaryPath;

    /**
     * 请求下载文件
     *
     * @param filePath
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping
    public void downloadFile(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断路劲是否在给定的范围内
        String path = this.downloadPath.replace("\\", "/");
        if (!filePath.startsWith(path)) {
            log.warn("{} 通过特殊手段下载文件 {} 大小 {} 已拒绝", Utils.getIp(request), filePath, Utils.byteConversion(new File(filePath).length()));
            return;
        }
        log.info("{} 请求下载文件 {} 大小 {}", Utils.getIp(request), filePath, Utils.byteConversion(new File(filePath).length()));
        Utils.fileDownload(filePath, response);
    }

    @GetMapping("/zip")
    public void downloadZip(String path, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //get传参 字符串分割成list
        ArrayList<String> list = new ArrayList<>(Arrays.asList(path.split(",")));

        // 创建压缩包文件
        File file = new File(this.temporaryPath + "\\" + UUID.randomUUID() + ".zip");
        log.info("用户 \t\t\t {} 请求打包下载文件 {}", Utils.getIp(request), list);
        log.info("生成压缩包文件 \t {} ", file.getName());

        try (
                //try()中的资源会自动关闭
                //zip输出流
                ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
                //zip输入流
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                //Servlet输出流
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            for (String s : list) {
                Utils.zipConstruct(s, zipOutputStream, this.downloadPath);
            }
            // 压缩包打包完成。关闭压缩包输出流，不关掉输入流没法读取
            zipOutputStream.close();
            log.info("压缩包 \t\t {} \t 打包完毕", file.getName());

            //响应压缩包，并返回大小
            response.reset();
            response.setContentType("application/x-zip-compressed");
            response.addHeader("Content-Length", String.valueOf(file.length()));

            log.info("将压缩包 \t\t {} \t 发送给用户", file.getName());
            /*int len;
            byte[] bytes = new byte[8192];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }*/

            //使用文件拷贝的方式实现下载
            IOUtils.copy(inputStream, outputStream);
            log.info("压缩包 \t\t {} \t 发送成功", file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("准备删除压缩包 \t {} ", file.getName());
            boolean delete = file.delete();
            if (delete) {
                log.info("压缩包 \t\t {} \t 删除成功", file.getName());
            } else {
                log.error("压缩包 \t\t {} \t 删除失败, 请手动删除压缩包", file.getName());
            }
        }

    }

}
