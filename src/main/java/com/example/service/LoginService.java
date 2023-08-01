package com.example.service;

import com.example.domain.entity.User;
import com.example.domain.dto.LoginDto;

public interface LoginService {

    User login(LoginDto loginDto);

    String getRole(String userId);

}
