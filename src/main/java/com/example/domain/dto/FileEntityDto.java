package com.example.domain.dto;

import com.example.domain.FileEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class FileEntityDto {

    private String announcement;
    private List list;

}
