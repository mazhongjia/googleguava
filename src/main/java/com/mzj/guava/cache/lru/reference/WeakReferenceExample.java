package com.mzj.guava.cache.lru.reference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 弱引用示例
 *
 * 比软引用还要脆弱（容易被回收）：不论是GC还是Full GC都会被回收
 */
public class WeakReferenceExample {
    public static void main(String[] args) throws InterruptedException {

        WeakReference<Ref> reference = new WeakReference<>(new Ref(0));
        //软引用
        int counter = 0;

        List<WeakReference<Ref>> container = new ArrayList<>();

        for (; ; ) {
            int current = counter++;
            container.add(new WeakReference(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container.");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
