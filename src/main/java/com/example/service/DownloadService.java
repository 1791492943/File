package com.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface DownloadService {

    void downloadFile(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException;

    void downloadZip(String path, HttpServletResponse response, HttpServletRequest request);

}
