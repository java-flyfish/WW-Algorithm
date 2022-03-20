package com.springdemo.test;

import com.springdemo.demo1.Config;
import com.springdemo.demo1.UserInterface;
import com.springdemo.demo1.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestSpring5 {

    @Test
    public void testBean(){

        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext(Config.class);
        UserInterface user = beanFactory.getBean("userService",UserInterface.class);
        System.out.println(user);
        user.test();
    }
}
