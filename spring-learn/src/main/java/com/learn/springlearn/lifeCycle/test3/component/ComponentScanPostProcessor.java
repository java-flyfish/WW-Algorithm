package com.learn.springlearn.lifeCycle.test3.component;

import com.learn.springlearn.lifeCycle.test3.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

/**
 * 自定义的BeanFactoryPostProcessor用来处理ComponentScan
 * P22
 */
public class ComponentScanPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory listableBeanFactory) throws BeansException {
        try {
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

                            //判断listableBeanFactory是否是DefaultListableBeanFactory，一般都是，
                            if (listableBeanFactory instanceof DefaultListableBeanFactory) {
                                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) listableBeanFactory;
                                //4.1获取beanName
                                AnnotationBeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();
                                String beanName = nameGenerator.generateBeanName(beanDefinition, beanFactory);

                                //4.2注册beanDefinition，这里也可以直接使用context注册
                                beanFactory.registerBeanDefinition(beanName,beanDefinition);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){

        }

    }
}
