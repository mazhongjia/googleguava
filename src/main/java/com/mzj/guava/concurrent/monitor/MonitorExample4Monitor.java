package com.mzj.guava.concurrent.monitor;

import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorExample4Monitor {

    public static void main(String[] args) {
        final MonitorGuard sync = new MonitorGuard();
        final AtomicInteger COUNTER = new AtomicInteger(0);

        //---------创建生产者线程-----------
        for (int i = 0; i <= 3; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        int data = COUNTER.getAndIncrement();
                        System.out.println(Thread.currentThread() + " offer " + data);
                        sync.offer(data);
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        //---------创建消费者线程-----------
        for (int i = 0; i <= 2; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        int data = sync.take();
                        System.out.println(Thread.currentThread() + " take " + data);
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    static class MonitorGuard {

        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        private final Monitor monitor = new Monitor();
        private final Monitor.Guard CAN_OFFER = monitor.newGuard(() -> queue.size() < MAX);
        private final Monitor.Guard CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());

        public void offer(int value) {
            try {
                //不需要使用while循环
                monitor.enterWhen(CAN_OFFER);//内部实现，其实用的就是JDK的Lock的lock + while循环，不满足条件时，block住了
                queue.addLast(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
        }

        public int take() {
            Integer value = null;
            try {
                //不需要使用while循环
                monitor.enterWhen(CAN_TAKE);//内部实现，其实用的就是JDK的Lock的lock + while循环，不满足条件时，block住了
                value = queue.removeFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
            return value;
        }
    }
}
