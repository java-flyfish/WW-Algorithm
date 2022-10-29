package com.springdemo.mybatis.factory;

import com.springdemo.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserFactoryBean implements FactoryBean {

    //接口
    private Class clazz;

    //构造方法
    public UserFactoryBean(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public Object getObject() throws Exception {
        //生成代理类
        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("userMapper的代理");
                return null;
            }
        });
        return o;
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }
}
