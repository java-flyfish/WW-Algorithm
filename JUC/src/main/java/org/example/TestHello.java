package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestHello {
    public static void main(String[] args) {
        Proxy.newProxyInstance(TestApi.class.getClassLoader(), new Class[]{TestApi.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }
}
