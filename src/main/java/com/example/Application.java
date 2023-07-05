package com.example;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.secure.SaBase64Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("项目启动成功...");
	}

	@Bean
	public TomcatServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> {
			connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}()=");
			connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}()=");
		});
		return factory;
	}

}
