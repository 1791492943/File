package com.example.globalException;

import com.example.common.R;
import com.example.globalException.exception.LoginException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(LoginException.class)
    public R loginException(LoginException e){
        return R.error(e.getMessage());
    }

}
