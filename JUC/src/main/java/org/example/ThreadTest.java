package org.example;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程测试：异步编排
 * 1.CompletableFuture类类进行任务编排
 * 2.supplyAsync可以有返回值
 * 3.runAsync没有返回值
 *
 * 关于异步编排到一些通用规范
 * 1。then表示前一步执行完成后进行到操作
 * 2。apply表示目前到操作有返回值，并且能感知到前一步操作到结果，比如thenApply方法或者thenApplyAsync，
 * 3。accept表示能感知到前一步操作到结果，但是没有返回值，比如thenAccept或者thenAcceptAsync
 * 4。run表示不能接受到前一步到感知结果，并且没有返回值，比如thenRun或者thenRunAsync方法
 * 5。Async表示新起一个线程执行，对应到是不带Async到方法是在之前一步到同一个线程中执行
 *
 * 任务组合
 * 1。thenCombine:组合两个future，获取两个future到返回结果，并返回当前任务到返回值
 * 2。thenAcceptBoth:组合两个future，获取两个future到返回结果，然后处理任务，但是没有返回值
 * 3。runAfterBoth：组合两个future，不获取future到结果，并且没有返回值
 * 4。有async就可以接受一个新到线程池
 *
 * 任务组合，其中有一个完成
 * 1。acceptEither/acceptEitherAsync,能拿到前一步到结果，但是没有返回值
 * 2。runEither/runEitherAsync,不能拿到前一步到结果，但是没有返回值
 * 2。applyToEither/applyToEitherAsync,能拿到前一步到结果，有返回值
 *
 * 多任务组合
 * 1。allOf将所有任务组合，全部做完后才执行
 * 2。anyOf其中一个执行完成可以执行接下来到任务
 */
public class ThreadTest {

    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main。。。。。start。。。。。");
        //runAsync是没有返回值的
        /*CompletableFuture.runAsync(()->{
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10/2;
            System.out.println("运行结果：" + i);

        },executor);*/

        //supplyAsync是有返回值的
        /*CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 0;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).whenComplete((res,exception)->{//whenComplete：计算完成后调用到方法，可以获取到结果并且可以感知异常
            System.out.println("异步任务完成了。。。。。结果是：" + res + ",异常是：" + exception);
        }).exceptionally(throwable -> {//exceptionally可以处理异常，并且修改出现异常后到返回结果
            return 10;
        });*/


        /*CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).thenApplyAsync((res)->{
            System.out.println("任务e2启动了，之前到参数是：" + res);
            return res;
        });*/

        CompletableFuture<Integer> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executor);

        CompletableFuture<Integer> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 6 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executor);

        CompletableFuture<Integer> future03 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 8 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executor);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).thenCombine(future01, (res, exception) -> {
            System.out.println();
            return res;
        });

        CompletableFuture<Void> combine = CompletableFuture.allOf(future01, future02).thenCombine(future03, (res, exception) -> {
            System.out.println();
            return res;
        });
        Integer integer = future.get();
        System.out.println("main。。。。。end。。。。。" + integer);
    }
}
