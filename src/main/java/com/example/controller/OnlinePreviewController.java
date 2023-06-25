package com.example.controller;

import com.example.common.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Arrays;

@RestController
@RequestMapping("/onlinePreview")
public class OnlinePreviewController {

    @GetMapping("/text")
    public R previewText(String absolutePath) throws IOException {
        File file = new File(absolutePath);
        if (!file.exists()) return R.error("预览文件不存在");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            StringBuffer sb = new StringBuffer();
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

}
