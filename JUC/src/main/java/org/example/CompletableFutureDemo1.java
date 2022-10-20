package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //无返回值
//        task0();
        //有返回值
//        task1();
        //任务A完成后执行任务B
//        task2();
        //thenApply案例
//        task3();
        //thenApply案例
        task4();

    }

    private static void task4() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "111";
        }).handle((v,e)->{
            if (e != null){
                System.out.println(e.getMessage());
            }
            return v + "222";
        }).handle((v,e)->{
            if (e != null){
                System.out.println(e.getMessage());
            }
            return v + "333";
        }).whenComplete((v,e)->{
            if (e == null){
                //没有抛出异常
                System.out.println("上部任务结果：" + v);
            }
        }).exceptionally(e->{
            System.out.println("执行任务发生异常");
            return "000";
        });
        Thread.sleep(4000);
    }

    private static void task3() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "111";
        }).thenApply((v)->{
            return v + "222";
        }).thenApply((v)->{
            return v + "333";
        }).whenComplete((v,e)->{
            if (e == null){
                //没有抛出异常
                System.out.println("上部任务结果：" + v);
            }
        }).exceptionally(e->{
            System.out.println("执行任务发生异常");
            return "000";
        });
        future.join();
    }

    /**
     * 任务A完成后执行任务B
     */
    private static void task2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).whenComplete((v,e)->{
            if (e == null){
                //没有抛出异常
                System.out.println("上部任务结果：" + v);
            }
        }).exceptionally(e->{
            System.out.println("执行任务发生异常");
            return 0;
        });
        System.out.println(future.get());
    }

    /**
     * 无返回值
     */
    private static void task0() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(future.get());
    }

    /**
     * 有返回值
     */
    private static void task1() throws InterruptedException, ExecutionException {
        CompletableFuture<String > future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Thread.currentThread().getName();
        });
        System.out.println(future1.get());
        System.out.println("主线程结束");
    }
}
