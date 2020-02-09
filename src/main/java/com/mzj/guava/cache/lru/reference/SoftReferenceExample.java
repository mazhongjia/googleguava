package com.mzj.guava.cache.lru.reference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 软引用示例
 */
public class SoftReferenceExample {
    public static void main(String[] args) throws InterruptedException {
//        /**
//         * detected the JVM process memory have little space try to GC soft reference\
//         *
//         * 1、检测到JVM进程内存空间有限，尝试GC软引用
//         * 2、软引用也有可能造成OOM
//         *
//         */
//        SoftReference<Ref> reference = new SoftReference<>(new Ref(0));
        SoftReference<Ref> reference = new SoftReference<>(new Ref(0));
        //软引用
        int counter = 0;

        List<SoftReference<Ref>> container = new ArrayList<>();

        for (; ; ) {
            int current = counter++;
            container.add(new SoftReference(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container.");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
