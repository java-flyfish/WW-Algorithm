package com.learn.springlearn.lifeCycle.test2;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 演示bean的后置处理器都有什么作用
 * P15-16节
 */
public class LifeCycle02Application {

    public static void main(String[] args) {

        //GenericApplicationContext是一个比较干净的容器，里面没有bean的后置处理器
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);
        context.registerBean("bean4",Bean4.class);

        //ContextAnnotationAutowireCandidateResolver是用来支持对"@Value"的值的获取
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        //注册Autowired后置处理器，如果没有ContextAnnotationAutowireCandidateResolver，这里会报错，因为不能解析"@Value"
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);//解析@Value和@Autowired
        context.registerBean(CommonAnnotationBeanPostProcessor.class);//解析@Resource、@PostConstruct、@PreDestroy

        //用于springboot中的@ConfigurationProperties解析和属性绑定
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());
        context.refresh();

        Object bean4 = context.getBean("bean4");
        System.out.println(bean4);
        context.close();
    }
}
