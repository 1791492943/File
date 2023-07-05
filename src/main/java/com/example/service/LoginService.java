package com.example.service;

import com.example.domain.entity.User;
import com.example.domain.vo.LoginVo;

public interface LoginService {

    User login(LoginVo loginVo);

}
