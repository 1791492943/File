package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.User;
import com.example.domain.vo.LoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {

    @Select("select * from user where account = #{account} and password = #{password}")
    User login(LoginVo loginVo);


    List<String> getPermissions(String userId);
}
