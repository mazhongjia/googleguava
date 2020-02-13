package com.mzj.guava.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * cache的preload（预加载）
 */
public class CacheLoaderTest5 {

    /**
     * 方案：
     *
     * 此方案存在问题：在创建CacheLoader时，限制（规定）了cache的value时key的uppercase
     *
     * 但是预加载的时候，可以不按照此规则向cache中放入数据
     *
     * 在进行业务逻辑处理时依然按照这个规则
     *
     * @throws InterruptedException
     */
    @Test
    public void testCachePreLoad() throws InterruptedException {

        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);//这里限制（规定）了cache的value时key的uppercase

        LoadingCache<String,String> cache = CacheBuilder.newBuilder()
                .build(cacheLoader);

        Map<String,String> preData = new HashMap<String,String>(){
            {
                put("alex","ALEX");
                put("mazhongjia","MAZHONGJIA");
                put("hello","hello");//没有按照规则向缓存放入数据
            }
        };

        cache.putAll(preData);//预加载

        assertThat(cache.size(),equalTo(2L));
        assertThat(cache.getUnchecked("alex"),equalTo("ALEX"));
    }


}
