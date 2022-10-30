package com.learn.springlearn.applicationContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 测试BeanFactory
 * 1.BeanFactory是不会主动去调用BeanFactory的后置处理器，也就是@Configuration等注解是不会去主动解析的
 * 2.BeanFactory也不会主动去调用BeanPostProcessor
 * 3.BeanFactory不会主动预先实例化单例bean
 * 4.BeanFactory不会解析${}、#{}
 */
@Slf4j
public class TestBeanFactory {

    public static void main(String[] args) {
        /**
         * DefaultListableBeanFactory是BeanFactory的一个实现类，也是spring容器中默认的BeanFactory实现类
         */
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //获取一个beanDefinition
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", beanDefinition);

        //第一次获取beanFactory中所有的bean名字
        getAllBeanName(beanFactory);
        String[] beanDefinitionNames;

        //往beanFactory注册一些后置处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        //第二次获取beanFactory中所有的bean名字
        getAllBeanName(beanFactory);

        //让后置处理器工作
        //拿到所有bean后置处理器
        Map<String, BeanFactoryPostProcessor> postProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor postProcessor : postProcessorMap.values()) {
            //执行beanFactory后置处理器
            postProcessor.postProcessBeanFactory(beanFactory);
        }

        //第三次获取beanFactory中所有的bean名字
        getAllBeanName(beanFactory);

        //获取bean1，并使用其功能，但是这个时候我们会发现Bean2没有注入，也就是@Autowired注解没生效
        //有了bean的后置处理器的时候就不能现在去拿bean了，否则bean会被缓存，里面的bean2还是null
//        Bean1 bean1 = beanFactory.getBean(Bean1.class);
//        System.out.println(bean1.getBean2());

        //这时候需要加上bean的后置处理器，针对bean的生命周期阶段提供扩展功能，例如依赖注入
        //拿到所有bean后置处理器，这里其实是需要将BeanPostProcessor和beanFactory建立联系，之前BeanPostProcessor只是beanFactory中的一个普通bean
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor postProcessor : beanPostProcessorMap.values()) {
            //添加bean后置处理器,bean后置处理器的执行是按照顺序来的
            beanFactory.addBeanPostProcessor(postProcessor);
        }
        //预先创建所有的单例对象
        beanFactory.preInstantiateSingletons();
        //获取bean1，并使用其功能，但是这个时候我们会发现Bean2没有注入，也就是@Autowired注解没生效
        Bean1 bean1 = beanFactory.getBean(Bean1.class);
        System.out.println(">>>>>>>>>>>>>>>>>>>");
        System.out.println(bean1.getBean2());
    }

    private static void getAllBeanName(DefaultListableBeanFactory beanFactory) {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            //这时候拿到的只有一个config，这时候beanFactory中还缺少相关解析@Configuration等这些注解的能力
            System.out.println(beanDefinitionName);
        }
    }

    @Configuration
    static  class Config{

        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }
    }
    @Slf4j
    static class Bean1{
        @Autowired
        private Bean2 bean2;

        public Bean1(){
            log.info("构造Bean1");
        }

        public Bean2 getBean2(){
            return bean2;
        }
    }
    @Slf4j
    static  class Bean2{
        public Bean2(){
            log.info("构造Bean2");
        }
    }
}

