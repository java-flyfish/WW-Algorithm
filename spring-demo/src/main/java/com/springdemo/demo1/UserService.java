package com.springdemo.demo1;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserService implements InitializingBean,UserInterface {

    private User user;

    public UserService(){
        System.out.println("UserService构造方法");
    }

    @Autowired
    public void setUser(User user) {
        System.out.println("UserService的set方法注入");
        this.user = user;
    }

    @Override
    public void test(){
        System.out.println("UserService业务逻辑");
    }

    /**
     * InitializingBean是spring提供对，会在属性值设置以后执行。
     * 该方法优先级会在InstantiationAwareBeanPostProcessor自定义处理逻辑之后
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserService的InitializingBean方法");
    }
}
