package com.example.interceptor;

import com.example.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class FileDownloadInterceptor implements HandlerInterceptor {

    private Boolean download;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!download){
            log.info("{} 请求下载文件, 配置中已关闭下载", Utils.getIp(request));
        }
        return this.download;
    }

    public FileDownloadInterceptor(Boolean download) {
        this.download = download;
    }

}
