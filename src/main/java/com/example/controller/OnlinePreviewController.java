package com.example.controller;

import com.example.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/onlinePreview")
public class OnlinePreviewController {

    @GetMapping("/text")
    public R previewText(String absolutePath) throws IOException {
        File file = new File(absolutePath);
        if(!file.exists()) return R.error("预览文件不存在");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null){
            sb.append(s).append("\n");
        }
        return R.success(sb);
    }

}
