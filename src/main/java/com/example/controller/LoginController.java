package com.example.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.common.R;
import com.example.domain.entity.User;
import com.example.domain.vo.LoginVo;
import com.example.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @GetMapping
    public R login(LoginVo loginVo) {
        User user = loginService.login(loginVo);
        StpUtil.login(user.getAccount());

        return R.success("登陆成功");
    }

    @GetMapping("/isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}
