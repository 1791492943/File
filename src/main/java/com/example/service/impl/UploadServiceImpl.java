package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.ConfigProperties;
import com.example.domain.entity.FileInfo;
import com.example.globalException.exception.ServiceException;
import com.example.globalException.exception.UploadException;
import com.example.mapper.UploadMapper;
import com.example.service.UploadService;
import com.example.utils.Utils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class UploadServiceImpl extends ServiceImpl<UploadMapper,FileInfo> implements UploadService {

    @Autowired
    private ConfigProperties configProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MultipartFile file) throws IOException {
        // 计算文件哈希值,判断文件是否存在
        InputStream is = file.getInputStream();
        String fileMd5 = DigestUtils.md5DigestAsHex(is);

        FileInfo one = this.getOne(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getHashValue, fileMd5));

        if(one != null) throw new ServiceException("文件已存在");

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return false;

        String suffix = originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        originalFilename = originalFilename.contains(".")
                ? originalFilename.substring(0, originalFilename.lastIndexOf("."))
                : originalFilename;
        int size = 0;
        File file1 = new File(configProperties.getUploadPath() + "/" + originalFilename + suffix);
        while (file1.exists()) {
            file1 = new File(configProperties.getUploadPath() + "/" + originalFilename + " " + "(" + ++size + ")" + suffix);
        }

        FileInfo fileInfo = FileInfo.builder()
                .originalName(file.getOriginalFilename())
                .finalName(file1.getName())
                .filePath(configProperties.getUploadPath())
                .size(file.getSize())
                .suffix(suffix)
                .uploadUser(Integer.parseInt(StpUtil.getLoginIdAsString()) - 10000)
                .hashValue(fileMd5)
                .build();

        boolean save = this.save(fileInfo);
        if (!save) {
            throw new UploadException("文件保存失败");
        }
        file.transferTo(new File(configProperties.getUploadPath() + "/" + file1.getName()));
        log.info("文件保存成功");
        return true;
    }
}
