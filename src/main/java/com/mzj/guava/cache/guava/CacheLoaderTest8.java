package com.mzj.guava.cache.guava;

import com.google.common.cache.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * cache的CacheBuilderSpec
 */
public class CacheLoaderTest8 {

    @Test
    public void testacheBuilderSpec() throws InterruptedException {

        String spec = "maximumSize=5,recordStats";
        CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(spec);

        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.from(cacheBuilderSpec)
                .build(cacheLoader);

        assertThat(cache.getUnchecked("alex"),equalTo("ALEX"));

        CacheStats stats = cache.stats();

        //目前还没有请求从cache中获取到数据
        assertThat(stats.hitCount(),equalTo(0L));//命中数
        assertThat(stats.missCount(),equalTo(1L));//miss数
        assertThat(stats.hitRate(),equalTo(0.0D));//命中率
        assertThat(stats.missRate(),equalTo(1.0D));//miss率
    }
}
