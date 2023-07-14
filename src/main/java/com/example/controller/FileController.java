package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.aop.annotation.PathCheck;
import com.example.common.R;
import com.example.globalException.exception.ServiceException;
import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @SaCheckPermission("admin:deleteFile")
    @DeleteMapping("/delete")
    @PathCheck
    public R delete(String absolutePath){
        fileService.delete(absolutePath);
        return R.success("删除成功");
    }

    @SaCheckPermission("admin:moveFile")
    @PutMapping("/move")
    @PathCheck
    public R move(String file,String directory){
        fileService.move(file,directory);
        return R.success("移动成功");
    }

    @SaCheckPermission("admin:newDirectory")
    @PostMapping("/newDirectory")
    @PathCheck
    public R newDirectory(String directory){
        String s = fileService.newDirectory(directory);
        return R.success(s);
    }

    @SaCheckPermission("admin:moveFile")
    @PutMapping("/rename")
    @PathCheck
    public R rename(String oldName, String newName){
        boolean rename = fileService.rename(oldName, newName);
        if(!rename) throw new ServiceException("重命名失败,可能是文件包含以下字符 \\ / : * ? \" < > |");
        return R.success("重命名成功");
    }
}
