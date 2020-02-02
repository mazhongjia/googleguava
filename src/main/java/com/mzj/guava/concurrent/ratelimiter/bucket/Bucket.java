package com.mzj.guava.concurrent.ratelimiter.bucket;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class Bucket {

    //桶
    private final ConcurrentLinkedQueue<Integer> container = new ConcurrentLinkedQueue<>();

    //桶的上沿
    private final static int BUCKET_LIMIT = 1000;//最多能向桶里放入1000个请求

    //桶的下沿
    private final RateLimiter limiter = RateLimiter.create(10);//做多1秒只能处理10个请求

    //是否允许放入桶的Monitor
    private final Monitor offerMonitor = new Monitor();
    //是否允许从桶中获取元素
    private final Monitor pollMonitor = new Monitor();

    //桶的上游向桶中放数据
    public void submit(Integer data) {
        if (offerMonitor.enterIf(offerMonitor.newGuard(() -> container.size() < BUCKET_LIMIT))) {
            try {
                container.offer(data);
                System.out.println(Thread.currentThread() + " submit data " + data + ",current size : " + container.size());
            } finally {
                offerMonitor.leave();
            }
        } else {
            //超过桶的上沿，则降级
            throw new IllegalStateException("The bucket is full.");
        }
    }

    public void takeTheConsumer(Consumer<Integer> consumer) {
        if (pollMonitor.enterIf(pollMonitor.newGuard(() -> !container.isEmpty()))) {
            try {
                System.out.println(Thread.currentThread() + " waiting " + limiter.acquire());
                consumer.accept(container.poll());
            } finally {
                pollMonitor.leave();
            }
        }
    }
}
