package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Work()).start();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(new Work());
        executorService.submit(new Work());
        executorService.submit(new Work());
        executorService.submit(new Work());
        executorService.submit(new Work());
        executorService.submit(new Work());
        Thread.sleep(10000);
        executorService.shutdown();
    }
}

class Work implements Runnable{
    @Override
    public void run() {
        ServiceContext.set(Thread.currentThread().getName());
        System.out.println("线程名字：" + ServiceContext.get());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("获取的线程名字：" + ServiceContext.get());
        //Thread thread = Thread.currentThread();
        //System.out.println(thread);
    }
}
