package org.example;

public class ServiceContext {
    private static ThreadLocal<Object> local = new ThreadLocal<>();

    public static void set(Object o){
        local.set(o);
    }

    public static Object get(){
        System.out.println("ThreadLocal" + local);
        return local.get();
    }
}
