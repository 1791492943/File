package com.example.mapper;

import com.example.domain.entity.User;
import com.example.domain.dto.LoginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {

    @Select("select * from user where account = #{account} and password = #{password}")
    User login(LoginDto loginDto);

    List<String> getPermissions(String userId);

    String getRole(String userId);
}
