package com.example.controller;

import com.example.util.Utils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

/**
 * 下载文件
 */
@RestController
@RequestMapping("/download")
@Slf4j
public class DownloadController {

    @Value("${project-file-path.download}")
    private String downloadPath;

    @Value("${project-file-path.temporary}")
    private String temporaryPath;

    /**
     * 请求下载文件
     * @param filePath
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping
    public void downloadFile(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断路劲是否在给定的范围内
        String path = this.downloadPath.replace("\\","/");
        if(!filePath.startsWith(path)){
            log.warn("{} 通过特殊手段下载文件 {} 大小 {} 已拒绝", Utils.getIp(request), filePath, Utils.byteConversion(new File(filePath).length()));
            return;
        }
        log.info("{} 请求下载文件 {} 大小 {}", Utils.getIp(request), filePath, Utils.byteConversion(new File(filePath).length()));
        Utils.fileDownload(filePath,response);
    }

    @GetMapping("/zip")
    public void downloadZip(String path, HttpServletResponse response) throws IOException {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(path.split(",")));

        // 创建压缩包流
        File file = new File(this.temporaryPath + "\\" + UUID.randomUUID() + ".zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));

        try {
            for (String s : list) {
                Utils.zipConstruct(s, zipOutputStream, this.downloadPath);
            }
            // 关闭压缩包流
            zipOutputStream.close();

            // 返回压缩包给前端
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            response.reset();
            response.setContentType("application/x-zip-compressed");
            response.addHeader("Content-Length", String.valueOf(file.length()));

            int len;
            byte[] bytes = new byte[8192];
            while ((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            inputStream.close();
            outputStream.close();

            file.delete();

        }catch (ZipException e){
            e.printStackTrace();
            zipOutputStream.close();
            boolean delete = file.delete();
            log.error("压缩文件失败，存在相同的文件 {}，{}",e.getMessage().replace("duplicate entry: ",""),delete?"压缩包已删除":"压缩包删除失败");
        }catch (FileNotFoundException e){
            e.printStackTrace();
            zipOutputStream.close();
            boolean delete = file.delete();
            log.error("压缩文件失败， {}，{}",e.getMessage(),delete?"压缩包已删除":"压缩包删除失败");
        }catch (IOException e){
            e.printStackTrace();
            if(e.getMessage().contains("Connection reset by peer")){
                zipOutputStream.close();
                boolean delete = file.delete();
                log.info("用户取消下载， {}",delete?"压缩包已删除":"压缩包删除失败");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            file.delete();
        }

    }

}
