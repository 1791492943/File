package com.example.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfo {

    private Integer id;
    private String originalName;
    private String finalName;
    private String filePath;
    private Long size;
    private String suffix;
    private String uploadDate;
    private Integer uploadUser;
    private String hashValue;

}
