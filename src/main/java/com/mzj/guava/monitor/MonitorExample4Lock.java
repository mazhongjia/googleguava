package com.mzj.guava.monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class MonitorExample4Lock {

    public static void main(String[] args) {
        final LockCondition sync = new LockCondition();
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

    static class LockCondition {
        private final ReentrantLock lock = new ReentrantLock();
        private Condition FULL_CONDITION = lock.newCondition();
        private Condition EMPTY_CONDITION = lock.newCondition();

        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        public void offer(int value) {
            try {
                lock.lock();
                //此处必须用while，否则是BUG，正式此处造成视觉不明显
                while (queue.size() >= MAX) {
                    FULL_CONDITION.await();
                }
                queue.addLast(value);
                EMPTY_CONDITION.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

//        private<T> void doAction(Consumer<T> consumer,T t){
//            try {
//                lock.lock();
//                consumer.accept(t);
//            }finally {
//                lock.unlock();
//            }
//        }

        public int take() {
            Integer value = null;
            try {
                lock.lock();
                //此处必须用while，否则是BUG，正式此处造成视觉不明显
                while (queue.isEmpty()) {
                    EMPTY_CONDITION.await();
                }
                value = queue.removeFirst();
                FULL_CONDITION.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return value;
        }
    }
}
