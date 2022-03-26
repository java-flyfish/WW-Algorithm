package com.springdemo.mybatis.factory;

import com.springdemo.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

public class UserBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public UserBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 重写扫描逻辑，spring的逻辑是扫描到BeanDefinition以后就注册到spring中，现在我们需要手动注册
     */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder definitionHolder : beanDefinitionHolders) {
            BeanDefinition beanDefinition = definitionHolder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClassName(UserFactoryBean.class.getName());
        }

        //注册BeanDefinition
        return beanDefinitionHolders;
    }

    /**
     * 判断扫描到的类是不是一个合法的bean
     * 我们在这里只需要判断是不是一个接口就好
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }
}
