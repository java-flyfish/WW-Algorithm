package com.learn.springlearn.lifeCycle.test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LifeCycleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LifeCycleApplication.class);
        context.close();
    }
}
