package com.example.domain.entity;

import lombok.Data;

@Data
public class Test {

    private Integer id;
    private String originalName;
    private String finalName;
    private String filePath;
    private String filePathName;
    private Long size;
    private String suffix;
    private String uploadDate;
    private Integer uploadUser;

}
