package com.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ImageService {
    void getImage(String absolutePath, HttpServletResponse response, HttpServletRequest request) throws IOException;
}
