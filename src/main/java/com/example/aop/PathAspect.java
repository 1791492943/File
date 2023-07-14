package com.example.aop;

import com.example.config.ConfigProperties;
import com.example.globalException.exception.ServiceException;
import com.example.globalException.exception.UploadException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Field;
import java.util.Objects;

@Configuration
@Aspect
@EnableAspectJAutoProxy
public class PathAspect {

    @Autowired
    private ConfigProperties configProperties;

    @Pointcut("@annotation(com.example.aop.annotation.Upload)")
    private void uploadService() {}

    @Before("uploadService()")
    public void upload() {
        boolean equals = Objects.equals("true", configProperties.getUploadService());
        if(!equals) throw new UploadException("上传服务已关闭");
    }

    @Pointcut("@annotation(com.example.aop.annotation.Download)")
    private void downloadService() {}

    @Before("downloadService()")
    public void download() {
        boolean equals = Objects.equals("true", configProperties.getDownloadService());
        if(!equals) throw new UploadException("下载服务已关闭");
    }

    @Pointcut("@annotation(com.example.aop.annotation.PathCheck)")
    private void pathCheckService() {}

    @Around("pathCheckService()")
    public Object pathCheck(ProceedingJoinPoint pjp) throws Throwable {
        for (Object arg : pjp.getArgs()) {
            if(!(arg instanceof String)){
                break;
            }
            String string = arg.toString().replace("/","\\");
            if(!"".equals(string) && !string.startsWith(configProperties.getPath())){
                throw new ServiceException("非法路径!");
            }
        }
        return pjp.proceed();
    }
    
}