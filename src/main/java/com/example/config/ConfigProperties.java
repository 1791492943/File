package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "project")
public class ConfigProperties {

    private String path;
    private String uploadPath;
    private String downloadPath;
    private String imagesPath;
    private String deletePath;
    private String uploadService;
    private String downloadService;
    private String announcement;
    private Integer size;

    public String getUploadPath() {
        return path + "\\" + uploadPath;
    }

    public String getDownloadPath() {
        return path + "\\" + downloadPath;
    }

    public String getImagesPath() {
        return path + "\\" + imagesPath;
    }

    public String getDeletePath() {
        return path + "\\" + deletePath;
    }
}
