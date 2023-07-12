package com.example.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operate {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Timestamp createDate;
    private Integer behavior;
    private String file;
    private String event;

}
