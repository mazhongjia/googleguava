package com.mzj.guava.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * cache的refresh
 */
public class CacheLoaderTest4 {

    @Test
    public void testCacheRefresh() throws InterruptedException {

        CacheLoader<String, Long> cacheLoader = CacheLoader.from(k -> System.currentTimeMillis());

        LoadingCache<String,Long> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(2,TimeUnit.SECONDS)//设置写入后刷新时间（刷新个人理解为过期），如果把这个设置注释掉，会测试失败，实现上，guava并不是内部有一个线程不断检查过期元素，而是只记录这个的刷新时间，请求时判断，如果超过这个时间，则不去缓存中拿
                .build(cacheLoader);

        Long result1 = cache.getUnchecked("Alex");
        TimeUnit.SECONDS.sleep(3);
        Long result2 = cache.getUnchecked("Alex");

        assertThat(result1.longValue() != result2.longValue(),equalTo(true));
    }


}
