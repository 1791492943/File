package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.Operate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateMapper extends BaseMapper<Operate> {
}
