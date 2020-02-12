package com.mzj.guava.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class WeakKeyTest {

    /**
     * 在JVM发生Minor GC、Major GC和Full GC时会将所有weak reference全部释放掉
     */
    @Test
    public void testWeakKey() throws InterruptedException {
        LoadingCache<String,Employee> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .weakKeys()
                .weakValues()
                .build(this.createCacheLoader());

        assertThat(cache.getUnchecked("Alex"),notNullValue());
        assertThat(cache.getUnchecked("Guava"),notNullValue());

        /**
         *  active method
         *
         *  Thread active design pattern
         */
        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);

        assertThat(cache.getIfPresent("Alex"),nullValue());
    }

    private final CacheLoader<String,Employee> createCacheLoader(){
        return CacheLoader.from(key -> new Employee(key,key,key));
    }
}
