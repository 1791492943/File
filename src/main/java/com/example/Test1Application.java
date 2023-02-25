package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;

@SpringBootApplication
@Slf4j
public class Test1Application {

	public static void main(String[] args) {
		SpringApplication.run(Test1Application.class, args);
		log.info("项目启动成功...");
	}

	@Bean
	public TomcatServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}()=");
				connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}()=");
			}
		});
		return factory;
	}

}
