package com.springdemo.mybatis;

import com.springdemo.mybatis.factory.UserBeanDefinitionRegistrar;
import com.springdemo.mybatis.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan("com.springdemo.mybatis")
//倒入UserBeanDefinitionRegistrar
@Import(UserBeanDefinitionRegistrar.class)
public class MybatisApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册当前配置
        applicationContext.register(MybatisApplication.class);
        applicationContext.refresh();

        //从容器中获取userService
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.getUser();
    }
}
