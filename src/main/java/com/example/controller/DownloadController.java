package com.example.controller;

import com.example.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

/**
 * 下载文件
 */
@RestController
@RequestMapping("/download")
@Slf4j
public class DownloadController {

    @Value("${project-file-path.download}")
    private String path;

    /**
     * 请求下载文件
     * @param filePath
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping
    public void list(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断路劲是否在给定的范围内
        String path = this.path.replace("\\","/");
        if(!filePath.startsWith(path)){
            log.warn("{} 通过特殊手段下载文件 {} 大小 {} 已拒绝", Utils.getIp(request), filePath, Utils.byteConversion(new File(filePath).length()));
            return;
        }
        log.info("{} 请求下载文件 {} 大小 {}", Utils.getIp(request), filePath, Utils.byteConversion(new File(filePath).length()));
        Utils.fileDownload(filePath,response);
    }

}
