package com.mzj.guava.cache.lru.linkedhashmap;

import com.google.common.base.Preconditions;
import com.mzj.guava.cache.lru.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap实现LRU算法
 *
 * 1、为什么不将LinkedHashLRUCache直接继承LinkedHashMap，而采用组合的方式（定义一个内部类继承LinkedHashMap，并且将其与LinkedHashLRUCache定义为组合关系）因为如果这样继承，则外部使用LinkedHashLRUCache时，将看到作为LinkedHashMap实现的所有接口，封装性不好，暴漏了接口中定义方法以外的方法
 * 2、This class is not the thread-safe class.
 * 3、利用 LinkedHashMap的特性：访问顺序是否保证顺序（设置成true）
 *
 * @param <K>
 * @param <V>
 */
public class LinkedHashMapLRUCache<K, V> implements LRUCache<K, V> {

    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V> {
        final private int limit;

        public InternalLRUCache(int limit) {
            super(16, 0.75f, true);//初始容量、扩容比例，访问顺序是否保证顺序，前两个参数initialCapacity与loadFactor在示例中采用HashMap的默认值，本例中不重要
            this.limit = limit;
        }

        /**
         * LRU缓存：需重写此方法，即删除元素 的条件
         * <p>
         * 此方法会在put、putAll等方法中调用，意在向集合中添加元素后，是否删除集合中最头的元素head(eldest)
         *
         * @param eldest
         * @return
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > limit;
        }
    }

    private final int limit;
    private final InternalLRUCache<K, V> internalLRUCache;

    public LinkedHashMapLRUCache(int limit) {
        Preconditions.checkArgument(limit > 0, "The limit must than zero.");
        this.limit = limit;
        this.internalLRUCache = new InternalLRUCache(limit);
    }

    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key) {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return this.internalLRUCache.size();
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public int limit() {
        return this.internalLRUCache.limit;
    }

    @Override
    public String toString() {
        return this.internalLRUCache.toString();
    }
}
