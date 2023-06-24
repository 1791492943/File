package com.example.controller;

import com.example.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 上传文件
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {

    @Value("${project-file-path.upload}")
    private String path;

    @PostMapping
    public void upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return;
        log.info("{} 上传文件 {} 大小 {} 到目录 {} ", Utils.getIp(request), file.getOriginalFilename(), Utils.byteConversion(file.getSize()), this.path);

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        int size = 0;
        File file1 = new File(this.path + "/" + originalFilename + suffix);
        while (file1.exists()) {
            file1 = new File(this.path + "/" + originalFilename + " " + "(" + ++size + ")" + suffix);
        }
        file.transferTo(new File(this.path + "/" + file1.getName()));
    }

}
