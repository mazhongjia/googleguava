package com.mzj.guava.concurrent.monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MonitorExample4Synchronized {

    public static void main(String[] args) {
        final Synchronized sync = new Synchronized();
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

    static class Synchronized {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        public void offer(int value) {
            synchronized (queue) {
                //此处必须用while，否则是BUG，正是此处造成视觉不明显：逻辑为如果生产者生产过快，则让其等待，直到有人消费
                while (queue.size() >= MAX) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.addLast(value);
                queue.notifyAll();
            }
        }

        public int take() {
            synchronized (queue) {
                //此处必须用while，否则是BUG，正式此处造成视觉不明显：逻辑为如果消费者消费过快，则让其等待，直到有人生产
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer value = queue.removeFirst();//先进先出的队列
                queue.notifyAll();
                return value;
            }
        }
    }
}
