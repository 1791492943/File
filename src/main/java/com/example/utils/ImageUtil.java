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
import java.util.List;
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
        // 如果是是文件夹 直接返回图片 （图片已提前存入byte[]数组）
        byte[] directory = isDirectory(absolutePath);
        // 当isDirectory() 不为空时 则表示该文件是文件夹 需要返回文件夹图片 并结束
        if(directory != null){
            outputStream.write(directory);
            return;
        }

        // 判断文件图片是否存在
        File imageFile = ImageUtil.imageExists(absolutePath);
        // 如果有图片 直接输出
        if (imageFile != null) {
            FileInputStream inputStream = new FileInputStream(imageFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            return;
        }

        // 没有图片 创建图片
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
    }

    /**
     * 根据传入的文件 去图片库中查找
     *
     * @param absolutePath 文件
     * @return 找到就返回图片文件，没找到就返回 null
     */
    private static File imageExists(String absolutePath) {
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

    private static byte[] isDirectory(String absolutePath){
        if (!absolutePath.contains(".")) {
            // 没有后缀的文件 直接不给图片
            if (!new File(absolutePath).isDirectory()) {
                return null;
            }
            // 文件夹
            return new byte[]{-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 16, 0, 0, 0, 16, 8, 6, 0, 0, 0, 31, -13, -1, 97, 0, 0, 1, 40, 73, 68, 65, 84, 120, 94, -115, -110, -19, 46, 3, 65, 20, -122, -9, 18, 92, -110, 63, -70, -86, 43, -2, -70, 29, 17, 95, 33, -124, 8, -47, 16, -62, -123, 52, -91, 104, 125, -107, 75, -112, 108, 119, -74, -69, 101, -43, 111, -15, 58, -17, -84, 110, 50, -74, 53, 51, -55, -101, -13, -21, 121, -50, -52, -100, -29, 121, -65, -25, -13, 101, 30, -61, -25, 0, -61, 110, -128, 15, -26, 41, 64, -10, 88, -53, -13, 80, -61, -5, -3, -100, -50, -37, -99, -92, 83, -19, -114, -72, -30, 16, 70, 122, 33, 57, -105, -100, 1, -55, -87, -28, 68, 114, 12, -12, -21, -110, 35, 32, 62, -108, 28, 96, -48, -87, -30, 47, -17, -79, -77, 11, -116, 120, 31, -125, -10, 24, 1, -81, -19, 2, 67, -19, 33, 29, 43, -112, 55, -69, -64, 80, -69, 72, 111, 103, -53, 2, 126, -106, 11, 12, -75, -125, -28, 102, -94, -64, 14, 35, -38, -98, 32, -112, 81, -71, -64, -120, -74, -112, 92, -5, 101, 1, 103, -20, 2, 127, -121, 107, -24, -73, 124, 100, -115, -23, -87, -78, -64, 2, 35, -38, -108, -91, 90, -108, 49, 46, 32, 105, -7, 13, 67, -64, 13, -77, -63, -24, 109, -24, -18, 95, -81, 75, -70, -102, 2, -39, 46, 27, -116, -34, 58, -30, -85, 74, 81, 13, 1, -41, -45, 6, 35, 92, 69, 124, 89, 41, -86, 41, 104, 83, -16, 63, -116, 112, 5, 74, 11, -14, 106, 8, -72, -98, 54, 24, -31, 50, 84, -109, -126, -68, -102, 2, 89, 79, 46, -120, -114, -52, -103, -97, -60, -16, -83, -68, 46, 59, 18, -118, -102, 51, -93, -102, -111, -5, 1, -1, -30, -106, -115, -114, 8, -31, -119, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126};

        }
        return null;
    }


}
