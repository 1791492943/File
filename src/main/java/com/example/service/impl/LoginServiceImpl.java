package com.example.service.impl;

import com.example.domain.entity.User;
import com.example.domain.vo.LoginVo;
import com.example.globalException.exception.LoginException;
import com.example.mapper.LoginMapper;
import com.example.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapper loginMapper;

    @Override
    public User login(LoginVo loginVo) {
        User user = loginMapper.login(loginVo);

        if (Objects.isNull(user)){
            throw new LoginException("登录失败,账号或密码错误");
        }

        return user;
    }

    @Override
    public String getRole(String userId) {
        return loginMapper.getRole(userId);
    }
}
