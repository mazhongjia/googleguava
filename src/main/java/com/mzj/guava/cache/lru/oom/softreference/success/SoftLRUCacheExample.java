package com.mzj.guava.cache.lru.oom.softreference.success;

import com.mzj.guava.cache.lru.linkedhashmap.LinkedHashMapLRUCache;

import java.util.concurrent.TimeUnit;

/**
 *  使用SoftReference解决LRU算法的OOM问题
 *
 *  本示例演示：使用SoftReference一定程度上缓解了OOM：体现在设置相同的Xmx参数，可以创建更多的缓存中元素，才导致OOM
 *
 * （设置：JVM启动参数：-Xmx128M -Xms64M -XX:+PrintGCDetails）
 */
public class SoftLRUCacheExample {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 直接向cache里放入了软引用
         */
        final SoftLRUCache<String, byte[]> cache = new SoftLRUCache<>(100);

        for (int i = 0; i < 100; i++) {
            cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);//每一个cache的value占用2兆数据
            TimeUnit.MILLISECONDS.sleep(600);
            System.out.println("The " + i + " entity is cached . ");
        }
    }
}
