package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.example.aop.annotation.Upload;
import com.example.common.R;
import com.example.domain.entity.FileEntity;
import com.example.domain.entity.FileInfo;
import com.example.mapper.UploadMapper;
import com.example.service.UploadService;
import com.example.utils.Utils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 上传文件
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {

    @Resource
    private UploadService uploadService;

    @SaCheckPermission("user:upload")
    @PostMapping
    @Upload
    public R upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        uploadService.save(file);
        return R.success("文件保存成功");
    }

}
