package com.example.controller;

import com.example.common.R;
import com.example.domain.FileEntity;
import com.example.domain.dto.FileEntityDto;
import com.example.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查询文件
 */
@RestController
@RequestMapping("/selectFile")
@Slf4j
public class SelectFileController {

    @Value("${project-file-path.download}")
    private String downloadPath;

    @Value("${setAnnouncement}")
    private String announcement;

    /**
     * 查询初始目录文件
     * @return
     */
    @GetMapping("/root")
    public R<FileEntityDto> selectInitialCatalogFile(HttpServletRequest request){
        log.info("{} 请求根目录文件", Utils.getIp(request));
        List<FileEntity> directoryFile = Utils.getDirectoryFile(this.downloadPath);
        FileEntityDto fileEntityDto = new FileEntityDto();
        fileEntityDto.setList(directoryFile);
        fileEntityDto.setAnnouncement(this.announcement);
        return R.success(fileEntityDto);
    }

    /**
     * 查询指定文件夹
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/directory")
    public R<List<FileEntity>> selectFile(@RequestBody FileEntity file, HttpServletRequest request){
        //判断路劲是否在给定的范围内
        String downloadPath = this.downloadPath.replace("\\","/");
        if(!file.getDirectoryName().startsWith(downloadPath)){
            log.warn("{} 通过特殊手段查看 {} 目录下的文件, 已拒绝", Utils.getIp(request), file.getDirectoryName());
            return null;
        }
        log.info("{} 查看 {} 目录下的文件", Utils.getIp(request), file.getDirectoryName());
        List<FileEntity> directoryFile = Utils.getDirectoryFile(file.getDirectoryName());
        return R.success(directoryFile);
    }

}
