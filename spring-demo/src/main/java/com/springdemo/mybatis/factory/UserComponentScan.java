package com.springdemo.mybatis.factory;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//倒入后该注解会封装到UserBeanDefinitionRegistrar的AnnotationMetadata方法参数中
@Import(UserBeanDefinitionRegistrar.class)
public @interface UserComponentScan {

    String value();
}
