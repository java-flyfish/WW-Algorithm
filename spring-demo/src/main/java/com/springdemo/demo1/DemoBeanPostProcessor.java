package com.springdemo.demo1;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * InstantiationAwareBeanPostProcessor实例化过程中的Bean后置处理器接口
 */
@Component
public class DemoBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)){
            System.out.println("userService初始化前");
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)){
            System.out.println("userService初始化后");
            Object o = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("userService代理逻辑");
                    Object invoke = method.invoke(bean, args);
                    return invoke;
                }
            });
            return o;
        }
        return null;
    }

    /**
     * 实例化后执行
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)){
            System.out.println("userService实例化后");
        }
        //如果这里执行以后不想再执行其他实例化处理逻辑，就返回false，false表示处理逻辑到此为止
        //比如返回false，则UserService中的set注入方法就不会执行。但是对PostConstruct没有影响
        return true;
    }

    /**
     * 实例化前，这时候还没有bean对象
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if ("userService".equals(beanName)){
            System.out.println("userService实例化前");
        }
        return null;
    }
}
