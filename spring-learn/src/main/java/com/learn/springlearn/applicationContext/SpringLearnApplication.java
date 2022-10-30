package com.learn.springlearn.applicationContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;

@SpringBootApplication
public class SpringLearnApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(SpringLearnApplication.class, args);

		//获取资源文件,classpath:是当前项目的路径
		Resource resource = context.getResource("classpath:application.properties");
		System.out.println(resource);

		//获取多个资源文件,classpath*:可以获取jar包中的配置文件
		Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
		for (Resource resource1 : resources) {
			System.out.println(resource1);
		}

	}

}
