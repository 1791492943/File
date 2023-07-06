package com.example.service.impl;

import com.example.globalException.exception.DownloadException;
import com.example.service.DownloadService;
import com.example.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class DownloadServiceImpl implements DownloadService {
    @Value("${project-file-path.download}")
    private String downloadPath;
    @Override
    public void downloadFile(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = this.downloadPath.replace("\\", "/");
        if (!filePath.startsWith(path)) {
            log.warn("{} 通过特殊手段下载文件 {} 大小 {} 已拒绝", Utils.getIp(request), filePath, Utils.byteConversion(new File(filePath).length()));
            throw new DownloadException("非法路径");
        }
        log.info("{} 请求下载文件 {} 大小 {}", Utils.getIp(request), filePath, Utils.byteConversion(new File(filePath).length()));
        Utils.fileDownload(filePath, response);
    }

    @Override
    public void downloadZip(String path, HttpServletResponse response, HttpServletRequest request) {
        //get传参 字符串分割成list
        ArrayList<String> list = new ArrayList<>(Arrays.asList(path.split(",")));

        ArrayList<String> files = new ArrayList<>();
        for (String s : list) {
            this.getFileDirectory(s,files);
        }

        String ip = Utils.getIp(request);
        log.info("{} 打包下载 {}",ip,files);

        try (
                ZipOutputStream zipOutputStream = new ZipOutputStream(
                        new BufferedOutputStream(response.getOutputStream())
                )
        ) {
            for (String s : files) {
                zipDownload(zipOutputStream, s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void zipDownload(ZipOutputStream zipOutputStream, String s) throws IOException {
        //处理格式
        s = s.replace("/", "\\");
        File file = new File(s);
        if (!file.exists()) return;

        //去掉下载路径，只保留前端所看到的样子
        String filePathName = s.replace(this.downloadPath + "\\", "");

        zipOutputStream.putNextEntry(new ZipEntry(filePathName));
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(s));
        IOUtils.copy(inputStream,zipOutputStream);
        inputStream.close();
    }

    private void getFileDirectory(String fileName, List<String> files){
        File file = new File(fileName);
        if(!file.isDirectory()){
            files.add(fileName);
        }else{
            List<String> list = Arrays.asList(file.list());
            for (String s : list) {
                getFileDirectory(file.getAbsolutePath() + "\\" + s,files);
            }
        }
    }
}
