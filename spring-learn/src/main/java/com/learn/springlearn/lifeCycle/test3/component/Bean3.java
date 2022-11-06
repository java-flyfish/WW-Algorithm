package com.learn.springlearn.lifeCycle.test3.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class Bean3 {

    public Bean3(){
        log.info("Bean2 构造器被执行。。。。。。。。");
    }
}
