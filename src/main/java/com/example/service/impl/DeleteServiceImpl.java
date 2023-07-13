package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.example.domain.entity.FileEntity;
import com.example.domain.entity.Operate;
import com.example.globalException.exception.ServiceException;
import com.example.mapper.OperateMapper;
import com.example.service.FileService;
import com.example.service.OperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class DeleteServiceImpl implements FileService {

    @Autowired
    private OperateMapper operateMapper;

    @Autowired
    private OperateService operateService;

    @Value("${project-file-path.delete}")
    private String delete;

    @Value("${project-file-path.download}")
    private String download;

    @Override
    public void delete(String absolutePath) {
        absolutePath = absolutePath.replace("/", "\\");
        if (!absolutePath.startsWith(download)) throw new RuntimeException("路径非法");

        deleteFile(absolutePath);
    }

    @Override
    public void move(String file, String directory) {
        file = file.replace("/", "\\");
        directory = directory.replace("/", "\\");
        if (!file.startsWith(download)) throw new RuntimeException("路径非法");
        if (!directory.startsWith(download)) throw new RuntimeException("路径非法");

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
        directory = directory.replace("/", "\\");
        if (!directory.startsWith(download)) throw new RuntimeException("路径非法");

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
            String suffix = file.getName().substring(file.getName().lastIndexOf("."));
            file.renameTo(new File(delete + "\\" + uuid + suffix));
            Operate operate = Operate.builder()
                    .userId(loginIdAsInt)
                    .createDate(new Timestamp(System.currentTimeMillis()))
                    .behavior(1)
                    .file(file.getAbsolutePath())
                    .event(uuid + suffix)
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
