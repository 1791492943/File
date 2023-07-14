package com.example.controller;

import com.example.aop.annotation.PathCheck;
import com.example.common.R;
import com.example.config.ConfigProperties;
import com.example.domain.entity.FileEntity;
import com.example.domain.dto.FileEntityDto;
import com.example.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询文件
 */
@RestController
@RequestMapping("/selectFile")
@Slf4j
public class SelectFileController {

    @Autowired
    private ConfigProperties configProperties;

    /**
     * 查询初始目录文件
     *
     * @return
     */
    @GetMapping("/root")
    public R<FileEntityDto> selectInitialCatalogFile(HttpServletRequest request) {
        log.info("{} 请求根目录文件", Utils.getIp(request));
        List<FileEntity> directoryFile = Utils.getDirectoryFile(configProperties.getDownloadPath());
        FileEntityDto fileEntityDto = new FileEntityDto();
        fileEntityDto.setList(directoryFile);
        fileEntityDto.setAnnouncement(configProperties.getAnnouncement());
        fileEntityDto.setRootPath(configProperties.getDownloadPath());
        return R.success(fileEntityDto);
    }

    /**
     * 查询指定文件夹
     *
     * @param directory
     * @param request
     * @return
     */
    @GetMapping("/directory")
    @PathCheck
    public R<List<FileEntity>> selectFile(String directory, HttpServletRequest request) {
        log.info("{} 查看 {} 目录下的文件", Utils.getIp(request), directory);
        List<FileEntity> directoryFile = Utils.getDirectoryFile(directory);
        return R.success(directoryFile);
    }

    /**
     * 搜索文件
     * @param fuzzyQuery
     * @return
     */
    @GetMapping("/{fuzzyQuery}")
    public R<List<FileEntity>> fuzzyQueryFile(@PathVariable String fuzzyQuery) {
        List<FileEntity> fileEntities = new ArrayList<>();
        Utils.findFileList(new File(configProperties.getDownloadPath()), fileEntities);
        fileEntities = fileEntities.stream()
                //高亮显示 有问题 暂时不用了
                /*.map(o -> {
                    o.setName(o.getName().replace(fuzzyQuery,"<em>" + fuzzyQuery + "</em>"));
                    return o;})*/
                .filter(item -> {
                    String itemToLow = item.getName().toLowerCase();
                    String fuzzyQueryToLow = fuzzyQuery.toLowerCase();
                    return itemToLow.contains(fuzzyQueryToLow);})
                .collect(Collectors.toList());
        return R.success(fileEntities);
    }
}
