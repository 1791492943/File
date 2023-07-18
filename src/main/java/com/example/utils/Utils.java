package com.example.utils;

import com.example.domain.entity.FileEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Slf4j
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
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Disposition", "attachment; filename="+ "download" + filePath.substring(filePath.lastIndexOf(".")));
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Length", "" + new File(filePath).length());
        byte[] bytes = new byte[8192];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }

        inputStream.close();
    }


    public static void zipConstruct(String filePath, ZipOutputStream zipOutputStream,String legalPath) throws IOException {
        String replace = filePath.replace("\\", "/");
        String replace1 = legalPath.replace("\\", "/");
        if(!replace.startsWith(replace1)) {
            log.warn("非法的下载请求 {} 已拦截", replace);
            return;
        }

        File file = new File(filePath);
        // 1.判断是否为目录
        if (file.isDirectory()) {
            // 2.是目录
            // 2.1判断是否为空文件夹，如果是空文件夹，直接添加该文件夹
            String[] list = file.list();
            if(list.length == 0){
                zipOutputStream.putNextEntry(new ZipEntry(filePath + "\\"));
            }
            for (String s : file.list()) {
                // 如果是一个文件夹则递归调用
                Utils.zipConstruct(filePath + "\\" + s, zipOutputStream, legalPath);
            }
        } else {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            String replace2 = filePath.replace("\\", "/");
            String replace3 = legalPath.replace("\\", "/");
            String replace4 = replace2.replace(replace3 + "/", "");
            zipOutputStream.putNextEntry(new ZipEntry(replace4));

            int len;
            byte[] bytes = new byte[8192];
            while ((len = fileInputStream.read(bytes)) != -1) {
                zipOutputStream.write(bytes, 0, len);
            }
            fileInputStream.close();
        }

    }

    public static void findFileList(File dir, List<FileEntity> fileNames) {
        if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
            return;
        }
        String[] files = dir.list();// 读取目录下的所有目录文件信息
        for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
            File file = new File(dir, files[i]);
            if (file.isFile()) {// 如果文件
                FileEntity fileEntity = new FileEntity();
                fileEntity.setDirectoryName(dir + "\\" + file.getName());
                fileNames.add(fileEntity);
            } else {// 如果是目录
                findFileList(file, fileNames);// 回调自身继续查询
            }
        }
    }

}
