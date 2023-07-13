package com.example.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class FileEntityDto {

    private String announcement;
    private List list;
    private String rootPath;

}
