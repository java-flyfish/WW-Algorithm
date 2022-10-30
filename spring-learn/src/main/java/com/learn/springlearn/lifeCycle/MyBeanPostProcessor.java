package com.learn.springlearn.lifeCycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    /**
     * bean销毁前
     */
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)){
            log.info("lifeCycleBean销毁前。。。。。。");
        }
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)){
            log.info("lifeCycleBean实例化前。。。。。。");
        }
        //如果不返回null，或跳过后面的实例化阶段，也就是返回的实例会替换目标实例
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)){
            log.info("lifeCycleBean实例化后，依赖注入之前。。。。。。");
        }
        //返回false会跳过依赖注入阶段
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)){
            log.info("lifeCycleBean，依赖注入阶段执行，如@Autoware、@Value、@Resource等。。。。。。");
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)){
            log.info("lifeCycleBean，初始化之前执行，。。。。。。");
        }
        //这里如果返回其他bean，原来的bean会被替换
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)){
            log.info("lifeCycleBean，初始化之后执行，。。。。。。");
        }
        //这里如果返回其他bean，原来的bean会被替换，可以执行代理增强
        return bean;
    }
}
