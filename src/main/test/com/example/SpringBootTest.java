package com.example;

import cn.dev33.satoken.secure.BCrypt;
import org.junit.jupiter.api.Test;

@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Test
    public void BCrypt(){
        String hashpw1 = BCrypt.hashpw("123456");
        System.out.println("123456 加密后: " + hashpw1);
        String hashpw2 = BCrypt.hashpw("123456");
        System.out.println("123456 加密后: " + hashpw2);

        System.out.println(hashpw1 + " 比对 123456 为: " + BCrypt.checkpw("123456", hashpw1));
        System.out.println(hashpw2 + " 比对 123456 为: " + BCrypt.checkpw("123456", hashpw2));
    }

}
