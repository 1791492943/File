package com.example.controller;

import com.example.aop.annotation.PathCheck;
import com.example.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    @PathCheck
    public void getImage(String absolutePath, HttpServletResponse response, HttpServletRequest request) throws IOException {
        imageService.getImage(absolutePath,response,request);
    }

}
