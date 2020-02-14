package com.mzj.guava.cache.guava;

import com.google.common.cache.*;
import org.hamcrest.core.Is;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * cache的stat
 */
public class CacheLoaderTest7 {

    @Test
    public void testCacheStat() throws InterruptedException {

        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .recordStats()//通过构造CacheBuilder时调用recordStats还开启记录CacheStats
                .build(cacheLoader);

        assertThat(cache.getUnchecked("alex"),equalTo("ALEX"));

        /**
         * 获取cache的状态对象，CacheStats是不可变对象，把CacheStats设计成final的好处是：如果有多个线程访问cache，不同线程获取CacheStats之间互不影响
         */
        CacheStats stats = cache.stats();

        //目前还没有请求从cache中获取到数据
        assertThat(stats.hitCount(),equalTo(0L));//命中数
        assertThat(stats.missCount(),equalTo(1L));//miss数
        assertThat(stats.hitRate(),equalTo(0.0D));//命中率
        assertThat(stats.missRate(),equalTo(1.0D));//miss率
    }


}
