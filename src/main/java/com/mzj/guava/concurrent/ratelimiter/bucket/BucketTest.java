package com.mzj.guava.concurrent.ratelimiter.bucket;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class BucketTest {
    public static void main(String[] args) {
        final Bucket bucket = new Bucket();
        final AtomicInteger DATA_CREATOR = new AtomicInteger(0);

        //生产者线程：5个线程向桶中提交数据(每个线程1秒提交5次请求，总计25个请求)
        IntStream.range(0,5).forEach(i -> {
            new Thread(() ->{
                for (;;){
                    int data = DATA_CREATOR.getAndIncrement();
                    bucket.submit(data);
                    try{
                        TimeUnit.MILLISECONDS.sleep(200L);
                    }catch (Exception e){
                        if(e instanceof IllegalStateException){
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }).start();
        });

        //消费者线程
        //生产者线程：也是5个线程从桶中拿请求进行处理，但是多少个线程不重要，因为限制为每秒只能处理10个
        IntStream.range(0,5).forEach(i -> {
            new Thread(() ->{
                for (;;){
                    bucket.takeTheConsumer(x-> System.out.println(Thread.currentThread()+ " W " + x));
                }
            }).start();
        });
    }
}
