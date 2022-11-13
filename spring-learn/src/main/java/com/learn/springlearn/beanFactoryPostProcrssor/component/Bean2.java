package com.learn.springlearn.beanFactoryPostProcrssor.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class Bean2 {

    public Bean2(){
        log.info("Bean2 构造器被执行。。。。。。。。");
    }
}
