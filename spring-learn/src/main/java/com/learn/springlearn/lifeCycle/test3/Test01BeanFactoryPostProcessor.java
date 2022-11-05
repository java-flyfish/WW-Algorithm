package com.learn.springlearn.lifeCycle.test3;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 演示BeanFactory后置处理器
 * P19-20
 */
public class Test01BeanFactoryPostProcessor {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config",Config.class);
        //ConfigurationClassPostProcessor是Beanfactpry后置处理器，可以处理@ComponentScan、@Bean、@Import、@ImportResource
        context.registerBean(ConfigurationClassPostProcessor.class);

        //MapperScannerConfigurer这是mybatis的Beanfactpry后置处理器，用来扫描@Mapper接口
        context.registerBean(MapperScannerConfigurer.class,bd ->
                bd.getPropertyValues()
                  .add("basePackage","com.learn.springlearn.lifeCycle.test3.component.mapper"));

        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        context.close();
    }
}
