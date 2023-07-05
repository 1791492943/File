package com.example.domain.entity;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String account;
    private String password;
    private String username;
    private Integer accountStatus;
    private String createDate;

}
