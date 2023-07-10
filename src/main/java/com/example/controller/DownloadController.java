package com.example.controller;

import com.example.service.DownloadService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.*;

/**
 * 下载文件
 */
@RestController
@RequestMapping("/download")
@Slf4j
public class DownloadController {

    @Resource
    private DownloadService downloadService;

    /**
     * 请求下载文件
     *
     * @param filePath 需要下载的文件路径
     */
    @GetMapping
    public void downloadFile(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        downloadService.downloadFile(filePath, request, response);
    }

    /**
     * 批量下载文件
     * @param path 需要下载的文件路径用 ,分割
     * @return 提示信息
     */
    @GetMapping("/zip")
    public void downloadZip(String path, HttpServletResponse response, HttpServletRequest request) {
        downloadService.downloadZip(path, response, request);
    }
}
