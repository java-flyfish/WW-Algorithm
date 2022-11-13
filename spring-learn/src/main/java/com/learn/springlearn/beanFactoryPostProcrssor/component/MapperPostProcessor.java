package com.learn.springlearn.beanFactoryPostProcrssor.component;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;

/**
 * Mapper接口扫描的PostProcessor
 * 用来处理Mapper接口的注册
 * P25
 */
public class MapperPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
        try {
            String path = "classpath*:com/learn/springlearn/lifeCycle/test3/component/mapper/**/*.class";
            //2.获得basePackages包下的资源，也可以直接使用context.getResources(path);获取Resource[]
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(path);
            for (Resource resource : resources) {
                //3.读取资源文件上的指定注解
                CachingMetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();
                MetadataReader metadataReader = readerFactory.getMetadataReader(resource);
                ClassMetadata classMetadata = metadataReader.getClassMetadata();
                if (classMetadata.isInterface()){
                    /**
                     * @Bean
                     * public MapperFactoryBean<Mapper1> mapper1(SqlSessionFactory sqlSessionFactory){
                     *     MapperFactoryBean<Mapper1> factoryBean = new MapperFactoryBean<>(Mapper1.class);
                     *     factoryBean.setSqlSessionFactory(sqlSessionFactory);
                     *     return factoryBean;
                     * }
                     * 下面的beanDefinition构建相当于是注释中的这一段代码
                     */
                    AbstractBeanDefinition bd = BeanDefinitionBuilder.genericBeanDefinition(MapperFactoryBean.class)
                            //设置构造器参数
                            .addConstructorArgValue(classMetadata.getClassName())
                            //设置注入模式，主要是针对SqlSessionFactory
                            .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                            .getBeanDefinition();

                    //这里用于生成bean的名字
                    AbstractBeanDefinition beanNameDb = BeanDefinitionBuilder.genericBeanDefinition(classMetadata.getClassName())
                            .getBeanDefinition();
                    AnnotationBeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();
                    String beanName = nameGenerator.generateBeanName(beanNameDb, beanFactory);

                    //4.2注册beanDefinition，这里也可以直接使用context注册
                    beanFactory.registerBeanDefinition(beanName,bd);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
