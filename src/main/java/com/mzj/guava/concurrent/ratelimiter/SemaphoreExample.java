package com.mzj.guava.concurrent.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 同一时刻，只能有3个线程，访问testSemaphore这个方法
 */
public class SemaphoreExample {

    /**
     * 信号量
     */
    private final static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        //IntStream.range(0,10)：返回[0,10)的IntStream，然后进行forEach循环，启动10个线程，这10个线程都去执行testSemaphore方法
        IntStream.range(0,10).forEach(i -> service.submit(SemaphoreExample::testSemaphore));
    }

    private static void testSemaphore() {
        try {
            //只有有3个线程同时从acquire获取
            //获取资源
            semaphore.acquire();
            System.out.println(Thread.currentThread() + " is coming and do work.");
            //ThreadLocalRandom.current().nextInt是在多线程环境下生成随机数的方式（jdk1.7）主要提高以前多线程下使用Random的性能
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
            //被控制的代码
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //回收资源
            semaphore.release();
            System.out.println(Thread.currentThread() + " release the semaphore.");
        }

    }


}
