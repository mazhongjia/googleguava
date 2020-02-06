package com.mzj.guava.concurrent.cache.lru.linkedlist;

import com.google.common.base.Preconditions;
import com.mzj.guava.concurrent.cache.lru.LRUCache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * LinkedList + HashMap 实现LRU算法
 *
 * 1、This class is not the thread-safe class.
 *
 * @param <K>
 * @param <V>
 */
public class LinkedListLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;
    private final LinkedList<K> keys = new LinkedList<>();//存放cache的key，也用来控制缓存元素是否存在+在缓存的位置
    private final Map<K, V> cache = new HashMap<>();//存值（key对应的值，value的顺序不重要，缓存的顺序依据key中）

    public LinkedListLRUCache(int limit) {
        this.limit = limit;
    }

    @Override
    public void put(K key, V value) {
        K newKey = Preconditions.checkNotNull(key);
        V newValue = Preconditions.checkNotNull(value);

        if (keys.size() >= limit) {
            K oldestKey = keys.removeFirst();//删掉头
            cache.remove(oldestKey);
        }
        keys.addLast(key);
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        //直接进行删除
        boolean exist = keys.remove(key);
        if (!exist) {
            //如果删除失败，证明缓存中没有，则返回null
            return null;
        }
        //如果删除成功，则证明缓存中存在，接下来把他加到缓存列表的最前面
        keys.addLast(key);
        //返回值
        return cache.get(key);
    }

    @Override
    public void remove(K key) {
        boolean exist = keys.remove(key);
        if (exist) {
            cache.remove(key);
        }
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void clear() {
        this.keys.clear();
        this.cache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (K key : keys) {
            builder.append(key.toString()).append("=").append(cache.get(key)).append(";");
        }
        return builder.toString();
    }
}
