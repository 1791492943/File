package com.example.controller;

import com.example.common.R;
import com.example.utils.ImageUtil;
import com.example.utils.Utils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @GetMapping
    public void getImage(String absolutePath, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String ip = Utils.getIp(request);
        log.info("{} 请求获取图片",ip);
        if("".equals(absolutePath)) return;
        response.reset();
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        ImageUtil.getImage(absolutePath,outputStream);
    }

}
