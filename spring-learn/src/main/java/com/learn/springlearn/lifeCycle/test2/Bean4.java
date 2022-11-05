package com.learn.springlearn.lifeCycle.test2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "java")
public class Bean4 {

    private String home;

    private String version;

    public void setHome(String home) {
        this.home = home;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Bean4{" +
                "home='" + home + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
