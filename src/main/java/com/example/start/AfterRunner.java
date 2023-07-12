package com.example.start;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class AfterRunner implements ApplicationRunner {

    @Value("${project-file-path.download}")
    private String download;
    @Value("${project-file-path.upload}")
    private String upload;
    @Value("${project-file-path.images}")
    private String images;
    @Value("${project-file-path.delete}")
    private String delete;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //判断目录是否存在,不存在则创建
        File file = new File(this.download);
        if(!file.exists()){
            log.info("下载目录 {} 不存在,正在自动创建",this.download);
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "下载目录 {} 创建成功" : "下载目录 {} 创建失败",this.download);
        }
        file = new File(this.upload);
        if(!file.exists()){
            log.info("上传目录 {} 不存在,正在自动创建",this.upload);
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "上传目录 {} 创建成功" : "上传目录 {} 创建失败",this.upload);
        }
        file = new File(this.images);
        if(!file.exists()){
            log.info("图片仓库目录 {} 不存在,正在自动创建",this.images);
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "图片仓库目录 {} 创建成功" : "图片仓库目录 {} 创建失败",this.images);
        }
        file = new File(this.delete);
        if(!file.exists()){
            log.info("回收站目录 {} 不存在,正在自动创建",this.delete);
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "回收站目录 {} 创建成功" : "回收站库目录 {} 创建失败",this.delete);
        }

    }

}
