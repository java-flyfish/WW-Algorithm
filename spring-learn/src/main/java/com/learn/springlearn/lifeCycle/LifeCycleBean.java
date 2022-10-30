package com.learn.springlearn.lifeCycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class LifeCycleBean {

    public LifeCycleBean(){
        log.info("构造器。。。。。。。");
    }

    @Autowired
    public void autowire(@Value("${PATH}") String name){
        log.info("依赖注入。。。。。。。" + name);
    }

    @PostConstruct
    public void init(){
        log.info("初始化。。。。。。。");
    }

    @PreDestroy
    public void destory(){
        log.info("销毁。。。。。。。");
    }
}
