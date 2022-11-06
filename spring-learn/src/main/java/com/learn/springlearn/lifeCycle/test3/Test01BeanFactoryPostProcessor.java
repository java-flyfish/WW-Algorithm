package com.learn.springlearn.lifeCycle.test3;

import com.learn.springlearn.lifeCycle.test3.component.ComponentScanPostProcessor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 演示BeanFactory后置处理器
 * P19-22
 */
public class Test01BeanFactoryPostProcessor {

    public static void main(String[] args) throws IOException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config",Config.class);
        //自定义的ComponentScanPostProcessor，beanFactory后置处理器
        //context.registerBean(ComponentScanPostProcessor.class);

        //已有的Beanfactpry后置处理器演示begin。。。。。。。。。
        //ConfigurationClassPostProcessor是Beanfactpry后置处理器，可以处理@ComponentScan、@Bean、@Import、@ImportResource
        /*context.registerBean(ConfigurationClassPostProcessor.class);

        //MapperScannerConfigurer这是mybatis的Beanfactpry后置处理器，用来扫描@Mapper接口
        context.registerBean(MapperScannerConfigurer.class,bd ->
                bd.getPropertyValues()
                  .add("basePackage","com.learn.springlearn.lifeCycle.test3.component.mapper"));
        */
        //已有的Beanfactpry后置处理器演示end。。。。。。。。。

        //模拟后置处理器原理begin。。。。。。。。。。。
        dealComponentScan(context);
        //模拟后置处理器原理end。。。。。。。。。。。
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        context.close();
    }

    /**
     * 模拟ComponentScan的后置处理器实现
     * 已经抽取的ComponentScanPostProcessor
     */
    private static void dealComponentScan(GenericApplicationContext context) throws IOException {
        //1.首先我眼要拿到ComponentScan注解，并拿到里面的basePackages
        ComponentScan annotation = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
        if (annotation != null){
            String[] packages = annotation.basePackages();
            for (String p :packages){
                System.out.println(p);
                String path = "classpath*:" + p.replace(".","/") + "/**/*.class";
                //2.获得basePackages包下的资源，也可以直接使用context.getResources(path);获取Resource[]
                PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resourcePatternResolver.getResources(path);
//                Resource[] resources = context.getResources(path);
                for (Resource resource : resources) {
                    System.out.println(resource);

                    //3.读取资源文件上的指定注解
                    CachingMetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();
                    MetadataReader metadataReader = readerFactory.getMetadataReader(resource);
                    //3.1获取resource资源文件上的注解
                    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                    boolean hasAnnotation = annotationMetadata.hasAnnotation(Component.class.getName());
                    boolean hasMetaAnnotation = annotationMetadata.hasMetaAnnotation(Component.class.getName());
                    System.out.println(resource + ":" + hasAnnotation + "......" + hasMetaAnnotation);

                    if (hasAnnotation || hasMetaAnnotation){
                        //4.读取资源文件到beanDefinition
                        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                                .genericBeanDefinition(metadataReader.getClassMetadata().getClassName()).getBeanDefinition();

                        //4.1获取beanName
                        AnnotationBeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();
                        String beanName = nameGenerator.generateBeanName(beanDefinition, context);

                        //4.2注册beanDefinition，这里也可以直接使用context注册
                        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
                        beanFactory.registerBeanDefinition(beanName,beanDefinition);
                    }
                }
            }
        }
    }
}
