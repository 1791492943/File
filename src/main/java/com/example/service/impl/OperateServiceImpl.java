package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.entity.Operate;
import com.example.mapper.OperateMapper;
import com.example.service.OperateService;
import org.springframework.stereotype.Service;

@Service
public class OperateServiceImpl extends ServiceImpl<OperateMapper, Operate> implements OperateService {
}
