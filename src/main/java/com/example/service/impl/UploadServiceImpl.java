package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.example.domain.entity.FileInfo;
import com.example.globalException.exception.UploadException;
import com.example.mapper.UploadMapper;
import com.example.service.UploadService;
import com.example.utils.Utils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Resource
    private UploadMapper uploadMapper;

    @Value("${project-file-path.upload}")
    private String path;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return false;

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        int size = 0;
        File file1 = new File(this.path + "/" + originalFilename + suffix);
        while (file1.exists()) {
            file1 = new File(this.path + "/" + originalFilename + " " + "(" + ++size + ")" + suffix);
        }

        FileInfo fileInfo = FileInfo.builder()
                .originalName(file.getOriginalFilename())
                .finalName(file1.getName())
                .filePath(path).filePathName(path + "\\" + file1.getName())
                .size(file.getSize())
                .suffix(suffix)
                .uploadUser(Integer.parseInt(StpUtil.getLoginIdAsString()) - 10000)
                .build();

        int save = uploadMapper.save(fileInfo);
        if(save == 0){
            throw new UploadException("文件保存失败");
        }
        file.transferTo(new File(this.path + "/" + file1.getName()));
        log.info("文件保存成功");
        return save > 0;
    }
}
