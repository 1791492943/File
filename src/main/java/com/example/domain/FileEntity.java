package com.example.domain;

import com.example.util.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
@Getter
@ToString
public class FileEntity {


    //文件的名称
    private String name;
    //文件的目录
    private String directory;
    //文件的目录+名称 绝对路径
    private String directoryName;
    //文件大小
    private String fileSize;
    //文件是否为目录
    private Boolean directoryYN;


    //给文件大小时使用字节，自动转换单位
    private void setFileSize(Long fileSize) {
        this.fileSize = Utils.byteConversion(fileSize);
    }

    public void setDirectoryName(String directoryName) {
        assignment(directoryName);
    }

    public FileEntity byFilePathGetFileEntity(String directoryName) {
        assignment(directoryName);
        return this;
    }

    private void assignment(String directoryName) {
        this.name = directoryName.substring(directoryName.lastIndexOf("/")+1);
        this.directory = directoryName.substring(0,directoryName.lastIndexOf("/"));
        this.directoryName = directoryName;
        this.directoryYN = true;

        if(!new File(directoryName).isDirectory()){
            this.setFileSize(new File(directoryName).length());
            this.directoryYN = false;
        }
    }


}
