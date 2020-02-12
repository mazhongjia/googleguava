package com.mzj.guava.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SoftKeyTest {

    /**
     * 在JVM在内存快不够时（快要OOM时）有可能~~会尝试~~~将所有soft reference释放掉
     *
     * 测试本示例，最好修改JVM参数：-Xmx128M -Xms64M -XX:+PrintGCDetails
     */
    @Test
    public void testSoftValues() throws InterruptedException {
        LoadingCache<String, EmployeeBig> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .softValues()
                .build(this.createCacheLoader());

        int i = 0;
        for (; ; ) {
            cache.put("Alex" + i, new EmployeeBig("Alex" + 1, "Alex" + 1, "Alex" + 1));
            System.out.println("The Empolyee [" + (i++) + "] is store into cache.");
            TimeUnit.MILLISECONDS.sleep(600);
        }
    }

    private final CacheLoader<String, EmployeeBig> createCacheLoader() {
        return CacheLoader.from(key -> new EmployeeBig(key, key, key));
    }
}
