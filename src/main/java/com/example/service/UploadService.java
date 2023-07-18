package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface UploadService extends IService<FileInfo> {
    boolean save(MultipartFile file) throws IOException, NoSuchAlgorithmException;
}
