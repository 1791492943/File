package com.example.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.common.R;
import com.example.domain.entity.User;
import com.example.domain.dto.LoginDto;
import com.example.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @GetMapping
    public R login(LoginDto loginDto) {
        User user = loginService.login(loginDto);
        StpUtil.login(user.getAccount());
        String role = loginService.getRole(user.getAccount());
        return R.success(role);
    }

}
