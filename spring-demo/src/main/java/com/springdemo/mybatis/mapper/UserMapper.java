package com.springdemo.mybatis.mapper;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select user")
    Object getUser();
}
