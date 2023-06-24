package com.example.interceptor;

import com.example.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class FileUploadInterceptor implements HandlerInterceptor {

    private Boolean upload;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!upload){
            log.info("{} 请求上传文件, 配置中已关闭上传", Utils.getIp(request));
        }
        return this.upload;
    }


    public FileUploadInterceptor(Boolean upload) {
        this.upload = upload;
    }
}
