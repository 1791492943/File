package com.example.service.impl;

import com.example.service.ImageService;
import com.example.utils.ImageUtil;
import com.example.utils.Utils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Override
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
