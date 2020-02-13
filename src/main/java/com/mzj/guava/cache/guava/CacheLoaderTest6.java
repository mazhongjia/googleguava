package com.mzj.guava.cache.guava;

import com.google.common.cache.*;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * cache的removal通知（缓存中元素删除后通知）
 */
public class CacheLoaderTest6 {

    @Test
    public void testCacheRemovedNotification() throws InterruptedException {

        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);//这里限制（规定）了cache的value时key的uppercase

        RemovalListener<String, String> listener = notification -> {
            //如果是被逐出的
            if (notification.wasEvicted()) {
                /**
                 * RemovalCause：逐出原因枚举
                 *      EXPLICIT：手工逐出（wasEvicted方法返回false，不会进入此if判断）
                 *      REPLACED：被替换了（wasEvicted方法返回false，不会进入此if判断）
                 *      COLLECTED：垃圾回收，GC的时候被逐出
                 *      EXPIRED：时间过期，被逐出
                 *      SIZE：数量逐出
                 */
                RemovalCause removalCause = notification.getCause();
                assertThat(removalCause, Is.is(RemovalCause.SIZE));
                assertThat(notification.getKey(), equalTo("Alex"));
            }
        };

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)//设置缓存数量为3
                .removalListener(listener)//添加remove listener
                .build(cacheLoader);

        cache.getUnchecked("Alex");
        cache.getUnchecked("mazhongjia");
        cache.getUnchecked("huna");
        cache.getUnchecked("maxiaotang");
    }


}
