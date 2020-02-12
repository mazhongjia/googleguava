package com.mzj.guava.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CacheLoaderTest2 {

    /**
     * CacheBuilder的expireAfterAccess接口测试
     *
     * 接口中access time会根据Write/Update/Read操作进行改变
     *
     * @throws InterruptedException
     */
    @Test
    public void testEvictionByAccessTime() throws InterruptedException {
        LoadingCache<String,Employee> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build(this.createCacheLoader());

        assertThat(cache.getUnchecked("Alex"),notNullValue());
        assertThat(cache.size(),equalTo(1L));

        TimeUnit.SECONDS.sleep(3);
        assertThat(cache.getIfPresent("Alex"),nullValue());

        assertThat(cache.getUnchecked("Guava"),notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"),notNullValue());//read操作
        TimeUnit.MILLISECONDS.sleep(990);
        assertThat(cache.getIfPresent("Guava"),notNullValue());//read操作
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"),notNullValue());//测试Read操作会修改access time
    }

    /**
     * CacheBuilder的expireAfterWrite接口测试
     *
     * 接口中write time会根据Write/Update操作进行改变
     *
     * @throws InterruptedException
     */
    @Test
    public void testEvictionByWriteTime() throws InterruptedException {
        LoadingCache<String,Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(this.createCacheLoader());

        assertThat(cache.getUnchecked("Alex"),notNullValue());
        assertThat(cache.size(),equalTo(1L));

        assertThat(cache.getUnchecked("Guava"),notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"),notNullValue());//read操作
        TimeUnit.MILLISECONDS.sleep(990);
        assertThat(cache.getIfPresent("Guava"),notNullValue());//read操作
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"),nullValue());//测试Read操作不会修改write time

    }

    private final CacheLoader<String,Employee> createCacheLoader(){
        return CacheLoader.from(key -> new Employee(key,key,key));
    }
}
