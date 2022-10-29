package org.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask测试
 * 可以实现异步任务并且有返回值
 * 1.FutureTask需要传入一个Callable实现
 * 2.通过Thread启动线程
 * 3.通过task.get()获取异步任务返回对结果
 * 4.优势：大幅提高程序性能；劣势：get方法容易导致阻塞、isDone方法轮询导致cpu空转
 */
public class FutureTaskDemo
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {

        FutureTask<String> task = new FutureTask<>(new MyWork());
        Thread t = new Thread(task);
        t.start();
        System.out.println("主线程。。。");
        String s = task.get();
        System.out.println(s);
    }
}
class MyWork implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("call方法进入。。。。");
        return "hello Callable!";
    }
}
