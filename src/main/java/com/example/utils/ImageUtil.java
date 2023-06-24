package com.example.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Component
public class ImageUtil {

    private static String imagesPath;

    @Value("${project-file-path.images}")
    public void setImagesPath(String imagesPath) {
        ImageUtil.imagesPath = imagesPath;
    }

    /**
     * 根据路径获取文件图片，并通过 outputStream 输出
     *
     * @param absolutePath 文件的绝对路径径
     * @param outputStream 将文件图片转换成流，并通过该参数输出
     * @throws IOException
     */
    public static void getImage(String absolutePath, OutputStream outputStream) throws IOException {
        File imageFile = ImageUtil.imageExists(absolutePath);
        // 如果有图片 直接输出
        if (imageFile != null) {
            FileInputStream inputStream = new FileInputStream(imageFile);
            IOUtils.copy(inputStream, outputStream);
            return;
        }

        //没有图片 创建图片
        // 读取windows文件的图标
        File file = new File(absolutePath); // 任意一个windows文件
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file); // 获取文件的图标

        // 转换成io流
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB); // 创建一个空白的BufferedImage对象
        Graphics g = image.createGraphics(); // 获取Graphics对象
        icon.paintIcon(null, g, 0, 0); // 将图标绘制到BufferedImage对象上
        g.dispose(); // 释放资源
        FileOutputStream fos = new FileOutputStream(imagesPath + "\\" + absolutePath.substring(absolutePath.lastIndexOf(".") + 1) + ".png"); // 创建一个FileOutputStream对象，指定输出文件名
        ImageIO.write(image, "png", fos); // 将BufferedImage对象写入到输出流中，指定输出格式为png
        fos.close(); // 关闭输出流

        // 图片已经保存 再次调用找到图片 运行上方输出
        ImageUtil.getImage(absolutePath, outputStream);
    }

    /**
     * 根据传入的文件 去图片库中查找
     *
     * @param absolutePath 文件
     * @return 找到就返回图片文件，没找到就返回 null
     */
    private static File imageExists(String absolutePath) {
        // 文件没有后缀 不返回图片
        if (!absolutePath.contains(".")) return null;

        // 判断图片是否存在
        File images = new File(imagesPath);
        // 获取文件后缀名 不带.
        String suffix = absolutePath.substring(absolutePath.lastIndexOf(".") + 1);
        for (String image : Objects.requireNonNull(images.list())) {
            // 图片结尾必须时png，所以开头就为，原始的后缀名
            if (image.startsWith(suffix)) {
                return new File(imagesPath + "\\" + image);
            }
        }

        return null;
    }


}
