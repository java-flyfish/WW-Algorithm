package com.learn.springlearn.lifeCycle.test2;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.env.StandardEnvironment;

/**
 * AutowiredAnnotationBeanPostProcessor是如何工作的
 * P17-18节
 */
public class LifeCycle03Application {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2",new Bean2());
        beanFactory.registerSingleton("bean3",new Bean3());
        //ContextAnnotationAutowireCandidateResolver是用来支持对"@Value"的值的获取
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        //${}解析器
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        //手动创建AutowiredAnnotationBeanPostProcessor
        AutowiredAnnotationBeanPostProcessor postProcessor = new AutowiredAnnotationBeanPostProcessor();
        //关联beanFactory
        postProcessor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
        postProcessor.postProcessProperties(null,bean1,"bean1");

        System.out.println(bean1);
        //Bean1{bean2=com.learn.springlearn.lifeCycle.test2.Bean2@1ce92674, bean3=null, name='${PATH}'}
        //bean2有值了，但是bean3没有，因为AutowiredAnnotationBeanPostProcessor不会执行@Resource
    }
}
