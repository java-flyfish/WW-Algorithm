package com.springdemo.demo1;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestSpringBeanLife {

    @Test
    public void testBean(){

        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext(Config.class);
        UserInterface user = beanFactory.getBean("userService",UserInterface.class);
        System.out.println(user);
        user.test();
    }
}
