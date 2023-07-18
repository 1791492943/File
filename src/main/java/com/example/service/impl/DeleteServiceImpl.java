package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.config.ConfigProperties;
import com.example.domain.entity.FileEntity;
import com.example.domain.entity.FileInfo;
import com.example.domain.entity.Operate;
import com.example.globalException.exception.ServiceException;
import com.example.mapper.UploadMapper;
import com.example.service.FileService;
import com.example.service.OperateService;
import com.example.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class DeleteServiceImpl implements FileService {

    @Autowired
    private OperateService operateService;

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public void delete(String absolutePath) {
        deleteFile(absolutePath);
    }

    @Override
    public void move(String file, String directory) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setDirectoryName(file);

        File file1 = new File(file);
        File file2 = new File(directory + "\\" + fileEntity.getName());
        boolean b = file1.renameTo(file2);
        Operate operate = Operate.builder()
                .createDate(new Timestamp(System.currentTimeMillis()))
                .userId(StpUtil.getLoginIdAsInt())
                .behavior(2)
                .file(file)
                .event("移动文件到：" + directory)
                .build();
        if (!b) throw new RuntimeException("移动失败");
        operateService.save(operate);
    }

    @Override
    public String newDirectory(String directory) {
        String fileName = "新建文件夹";
        String num = "(0)";
        while (true) {
            File file = new File(directory + "\\" + fileName + (num.equals("(0)") ? "" : num));
            if (file.exists()) {
                num = "(" + (Integer.parseInt(num.substring(1, num.length() - 1)) + 1) + ")";
            } else {
                file.mkdirs();
                Operate operate = Operate.builder()
                        .createDate(new Timestamp(System.currentTimeMillis()))
                        .userId(StpUtil.getLoginIdAsInt())
                        .behavior(4)
                        .file(file.getAbsolutePath())
                        .event("新建文件夹")
                        .build();

                operateService.save(operate);
                return file.getName();
            }
        }
    }

    @Override
    public boolean rename(String oldName, String newName) {
        File file = new File(oldName);
        File file1 = new File(newName);
        if(file1.exists()) throw new ServiceException("文件夹已存在");
        boolean b = file.renameTo(file1);
        return b;
    }

    private void deleteFile(String absolutePath) {
        File file = new File(absolutePath);
        if (!file.exists()) return;
        Integer loginIdAsInt = StpUtil.getLoginIdAsInt();

        if (file.isFile()) {
            String uuid = UUID.randomUUID().toString();
            String newName = "";

            if(file.getName().contains(".")){
                String suffix = file.getName().substring(file.getName().lastIndexOf("."));
                file.renameTo(new File(configProperties.getDeletePath() + "\\" + uuid + suffix));
                newName = uuid + suffix;
            }else{
                file.renameTo(new File(configProperties.getDeletePath() + "\\" + uuid));
                newName = uuid;
            }

            Operate operate = Operate.builder()
                    .userId(loginIdAsInt)
                    .createDate(new Timestamp(System.currentTimeMillis()))
                    .behavior(1)
                    .file(file.getAbsolutePath())
                    .event(newName)
                    .build();
            operateService.save(operate);
        }

        if (file.isDirectory()) {
            String[] list = file.list();
            if (list.length == 0) file.delete();
            for (String s : list) {
                deleteFile(absolutePath + "\\" + s);
            }
            file.delete();
        }
    }
}
