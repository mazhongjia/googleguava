package com.mzj.guava.cache.lru.oom.softreference.success;

import com.mzj.guava.cache.lru.LRUCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 使用SoftReference解决LRU算法的OOM问题
 */
public class SoftLRUCache<K, V> implements LRUCache<K, V> {

    //这里的范型K,V1与SoftLRUCache的范型是两回事
    private static class InternalLRUCache<K, V1> extends LinkedHashMap<K, SoftReference<V1>> {//LinkedHashMap的值类型修改为SoftReference<V1>，即软引用类型
        final private int limit;

        public InternalLRUCache(int limit) {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, SoftReference<V1>> eldest) {
            return this.size() > limit;
        }
    }

    private final int limit;
    private final InternalLRUCache<K, V> cache;

    public SoftLRUCache(int limit) {
        this.limit = limit;
        this.cache = new InternalLRUCache<>(limit);
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key,new SoftReference<>(value));
    }

    @Override
    public V get(K key) {
        SoftReference<V> reference = this.cache.get(key);
        if (reference == null){
            return null;
        }
        return reference.get();
    }

    @Override
    public void remove(K key) {
        this.cache.remove(key);
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }
}
