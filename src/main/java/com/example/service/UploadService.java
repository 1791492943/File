package com.example.service;

import com.example.domain.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    boolean save(MultipartFile file) throws IOException;
}
