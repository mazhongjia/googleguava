package com.mzj.guava.concurrent.ratelimiter.tokenBucket;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 场景：小米手机搞促销，限时出售100台便宜的小米手机，同时采用令牌桶算法限制1秒钟，只能有10个人抢的到
 */
public class TokenBucket {

    private AtomicInteger phoneNumbers = new AtomicInteger(0);
    //这一时间段，只发出去100台手机进行销售
    private final static int LIMIT = 100;
    private RateLimiter rateLimiter = RateLimiter.create(10);//速率为1秒钟只允许卖10台

    private final int saleLimit;

    public TokenBucket() {
        this(LIMIT);
    }

    public TokenBucket(int limit) {
        this.saleLimit = limit;
    }

    /**
     * 抢购
     *
     * @return
     */
    public int buy() {

        Stopwatch started = Stopwatch.createStarted();//打印一下，花了多长时间抢到的手机

        //最多  等待10秒钟进行抢购（如果成功获取到令牌了，就证明只要还有存货，抢购就成功了）
        boolean success = rateLimiter.tryAcquire(10, TimeUnit.SECONDS);
        if (success) {
            //mzj：此处获取状态与维护phoneNumbers时必须要进行同步，因为rateLimiter获取令牌，仅仅控制了调用rateLimiter.tryAcquire后进入下面方法体的速率（不管几个线程），而并未同步进入方法体的线程！！！
            synchronized (phoneNumbers){
                //这一时间段，如果已经销售的手机超过了上限（这里是100台），则抛出异常，告诉客户端抢购商品已经抢没了
                if (phoneNumbers.get() >= saleLimit) {
                    throw new IllegalStateException("Not any phone can be sale, please wait to next time .");
                }
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //抢到了！
                int phoneNo = phoneNumbers.getAndIncrement();
                //处理订单信息
//                handlerOrder();
                System.out.println(Thread.currentThread() + " user get the MI phone: " + phoneNo + ", " + started.stop());
                return phoneNo;
            }

        } else {
            started.stop();
            //如果等待10秒还是没有抢到，快速失败策略
            throw new RuntimeException("Sorry，occur exception when buy phone");
        }
    }

    /**
     * 模拟处理订单
     */
    private void handlerOrder() {
        try {
            //处理库存-1，通常这里是异步调用库存服务接口
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
