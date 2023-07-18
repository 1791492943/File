package com.example.utils;

import cn.dev33.satoken.stp.StpInterface;
import com.example.mapper.LoginMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private LoginMapper loginMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissions = loginMapper.getPermissions(loginId.toString());
        return permissions;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String role = loginMapper.getRole(loginId.toString());
        return Arrays.asList(role);
    }
}
