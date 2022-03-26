package com.springdemo.mybatis.service;

import com.springdemo.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void getUser() {
        System.out.println(userMapper.getUser());
    }
}
