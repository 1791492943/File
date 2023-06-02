package com.example.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class DownloadZipDto {
    private List<String> paths;
}
