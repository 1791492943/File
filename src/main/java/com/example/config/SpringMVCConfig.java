package com.example.config;

import com.example.interceptor.FileDownloadInterceptor;
import com.example.interceptor.FileUploadInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SpringMVCConfig implements WebMvcConfigurer {

    @Value("${fileServer.upload}")
    private Boolean upload;

    @Value("${fileServer.download}")
    private Boolean download;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FileUploadInterceptor(upload)).addPathPatterns("/upload");
        registry.addInterceptor(new FileDownloadInterceptor(download)).addPathPatterns("/download");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

}
