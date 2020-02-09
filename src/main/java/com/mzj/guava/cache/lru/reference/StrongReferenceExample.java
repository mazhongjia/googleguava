package com.mzj.guava.cache.lru.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 强引用示例
 */
public class StrongReferenceExample {
    public static void main(String[] args) throws InterruptedException {
        //强引用
        int counter = 0;

        List<Ref> container = new ArrayList<>();

        for (; ; ) {
            int current = counter++;
            container.add(new Ref(current));
            System.out.println("The " + current + " Ref will be insert into container.");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
