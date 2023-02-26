package com.example.util;

import com.example.domain.FileEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {

    /**
     * 字节转换单位
     *
     * @param size 字节单位
     * @return
     */
    public static String byteConversion(Long size) {
        //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize;
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B";
        }
        return resultSize;
    }

    /**
     * 获取文件列表
     *
     * @param pathName 目录
     * @return
     */
    public static List<FileEntity> getDirectoryFile(String pathName) {
        //创建列表
        List<FileEntity> fileEntities = new ArrayList<>();
        //获取目录下的文件列表
        File[] files = new File(pathName).listFiles();

        //判断是否为空
        if (files == null) {
            return null;
        }

        //读取目录文件
        for (File file : files) {
            // 将文件绝对路径赋值给文件实体对象,并添加
            String path = file.getPath();
            path = path.replace("\\", "/");
            FileEntity fileEntity = new FileEntity();
            fileEntity.setDirectoryName(path);
            fileEntities.add(fileEntity);
        }
        return fileEntities;
    }

    public static String getIp(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }

    /**
     * 通过路劲下载文件
     *
     * @param filePath
     * @param response
     * @throws FileNotFoundException
     */
    public static void fileDownload(String filePath, HttpServletResponse response) throws IOException {
        //创建文件输入流
        FileInputStream inputStream = new FileInputStream(filePath);
        //创建文件输出流
        ServletOutputStream outputStream = response.getOutputStream();

        response.reset();
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Length", "" + new File(filePath).length());


        byte[] bytes = new byte[8192];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }

        outputStream.close();
        inputStream.close();

    }



}
