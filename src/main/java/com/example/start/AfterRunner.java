package com.example.start;

import com.example.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class AfterRunner implements ApplicationRunner {

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //判断目录是否存在,不存在则创建
        File file = new File(configProperties.getDownloadPath());
        if(!file.exists()){
            log.warn("下载目录 {} 不存在,正在自动创建",configProperties.getDownloadPath());
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "下载目录 {} 创建成功" : "下载目录 {} 创建失败",configProperties.getDownloadPath());
        }
        file = new File(configProperties.getUploadPath());
        if(!file.exists()){
            log.warn("上传目录 {} 不存在,正在自动创建",configProperties.getUploadPath());
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "上传目录 {} 创建成功" : "上传目录 {} 创建失败",configProperties.getUploadPath());
        }
        file = new File(configProperties.getImagesPath());
        if(!file.exists()){
            log.warn("图片仓库目录 {} 不存在,正在自动创建",configProperties.getImagesPath());
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "图片仓库目录 {} 创建成功" : "图片仓库目录 {} 创建失败",configProperties.getImagesPath());
        }
        file = new File(configProperties.getDeletePath());
        if(!file.exists()){
            log.warn("回收站目录 {} 不存在,正在自动创建",configProperties.getDeletePath());
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "回收站目录 {} 创建成功" : "回收站库目录 {} 创建失败",configProperties.getDeletePath());
        }

    }

}
