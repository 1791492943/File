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
    @Value("${project-file-path.temporary}")
    private String temporary;

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
        file = new File(this.temporary);
        if(!file.exists()){
            log.info("临时压缩目录 {} 不存在,正在自动创建",this.temporary);
            temporaryError();
            boolean mkdirs = file.mkdirs();
            log.info(mkdirs ? "临时压缩目录 {} 创建成功" : "临时压缩目录 {} 创建失败",this.temporary);
        }
        temporaryError();
    }

    private void temporaryError() {
        if(this.temporary.startsWith(this.download + "\\")){
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.error("临时文件不能放在下载目录中 {} ",this.download);
            log.error("当使用批量下载时，会在临时文件中创建一个压缩包");
            log.error("当用户将临时文件也勾选批量下载时");
            log.error("会无限制的在临时压缩包文件写入数据，直到磁盘被沾满为止");
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.exit(1);
        }
    }
}
