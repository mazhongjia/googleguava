package com.mzj.guava.cache.lru.oom.softreference;

import com.mzj.guava.cache.lru.linkedhashmap.LinkedHashMapLRUCache;

import java.util.concurrent.TimeUnit;

/**
 *  使用SoftReference解决LRU算法的OOM问题
 *
 *  本示例演示：虽然实现了LRU算法，但是实现的cache导致了OOM
 *
 * （设置：JVM启动参数：-Xmx128M -Xms64M -XX:+PrintGCDetails）
 */
public class LRUCacheExample {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 直接向cache里放入了强引用
         */
        final LinkedHashMapLRUCache<String, byte[]> cache = new LinkedHashMapLRUCache<>(100);

        for (int i = 0; i < 100; i++) {
            cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);//每一个cache的value占用2兆数据
            TimeUnit.MILLISECONDS.sleep(600);
            System.out.println("The " + i + " entity is cached . ");
        }
    }
}
