package com.example.controller;

import com.example.common.R;
import com.example.utils.ImageUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @GetMapping("/{absolutePath}")
    public R getImage(@PathVariable String absolutePath, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        ImageUtil.getImage(absolutePath,outputStream);
        return R.success("图片下载成功");
    }

}
