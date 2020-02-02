package com.mzj.guava.concurrent.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class RateLimiterExample {

    /**
     * 1秒钟只允许有0.5次操作，也就是只允许2秒执行一次操作（testLimiter方法）
     */
    private final static RateLimiter limiter = RateLimiter.create(0.5);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0,10).forEach(i -> service.submit(RateLimiterExample::testLimiter));
    }

    private static void testLimiter(){
        System.out.println(Thread.currentThread().getName() + " waiting " + limiter.acquire());
        //被控制的代码。。。
    }
}
