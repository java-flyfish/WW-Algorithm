package com.learn.springlearn.applicationContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestApplicationContext {

    public static void main(String[] args) {
//        testClassPathXmlApplicationContext();
//        testFileSystemXmlApplicationContext();
//        testAnnotationConfigApplicationContext();
        testAnnotationConfigServletWebServerApplicationContext();
    }

    /**
     * 基于web环境的实现springboot实现
     */
    private static void testAnnotationConfigServletWebServerApplicationContext() {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    /**
     * 非web环境的springboot实现
     */
    private static void testAnnotationConfigApplicationContext() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        Bean1 bean = context.getBean(Bean1.class);
        System.out.println(bean.getBean2());
    }

    private static void testFileSystemXmlApplicationContext() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("/spring-learn/src/main/resources/b01.xml");
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        Bean1 bean = context.getBean(Bean1.class);
        System.out.println(bean.getBean2());
    }

    /**
     * 最经典的ApplicationContext，基于classpath下的xml文件来创建
     * 这个ApplicationContext默认是没有beanFactory/bean后置处理器的，需要通过 <context:annotation-config/>标签来加上
     */
    private static void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("b01.xml");
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        Bean1 bean = context.getBean(Bean1.class);
        System.out.println(bean.getBean2());

        /**
         * ClassPathXmlApplicationContext或者FileSystemXmlApplicationContext
         * 与DefaultListableBeanFactory的关系
         * 内部是有一个DefaultListableBeanFactory，通过下面的方式加载类
         * begin。。。。。。。
         */
        /*DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("b01.xml"));
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }*/
        /**
         * end。。。。。。。。。
         */
    }

    /**
     * web容器相对更加复杂，要使web环境能够运行，需要依赖以下组件
     * 1.需要一个基于servlet技术的web容器：ServletWebServerFactory，这里我们可以使用TomcatServletWebServerFactory
     * 2.还需要前端控制器：DispatcherServlet
     * 3.需要将上面两个组件关联，将DispatcherServlet注册到ServletWebServerFactory，需要DispatcherServletRegistrationBean
     * 理论上有上面的组件，web容器就能够运行了，但是目前是没什么效果的，需要一个控制器也就是COntroller来呈现效果
     * 4.还要有一个controller
     * 备注：实际上springboot就是帮助我们隐藏了WebConfig所配置的这些bean
     */
    @Configuration
    static class WebConfig{
        /**
         * 这其实就是内嵌的tomcat
         */
        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }

        /**
         * 所有web请求的入口都是DispatcherServlet
         */
        @Bean
        public DispatcherServlet dispatcherServlet(){
            return new DispatcherServlet();
        }

        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet){
            return new DispatcherServletRegistrationBean(dispatcherServlet,"/");
        }

        /**
         * 这里有一个约定，如果bean的名字以"/"开头，并且实现了Controller接口，那这就是控制器
         */
        @Bean("/hello")
        public Controller controller(){
            return (request, response) -> {
                response.getWriter().println("hello");
                return null;
            };
        }
    }

    @Configuration
    static class Config{

        @Bean
        public Bean1 bean1(Bean2 bean2){
            Bean1 bean1 = new Bean1();
            bean1.setBean2(bean2);
            return bean1;
        }

        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }

    }
    @Slf4j
    static class Bean1{

        private Bean2 bean2;

        public Bean1(){
            log.info("构造Bean1");
        }

        public void setBean2(Bean2 bean2) {
            this.bean2 = bean2;
        }

        public Bean2 getBean2(){
            return bean2;
        }
    }
    @Slf4j
    static   class Bean2{
        public Bean2(){
            log.info("构造Bean2");
        }
    }
}


