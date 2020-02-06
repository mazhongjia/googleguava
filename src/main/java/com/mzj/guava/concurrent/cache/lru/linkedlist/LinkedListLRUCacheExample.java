package com.mzj.guava.concurrent.cache.lru.linkedlist;

import com.mzj.guava.concurrent.cache.lru.LRUCache;

public class LinkedListLRUCacheExample {
    public static void main(String[] args) {
        LRUCache<String,String> cache = new LinkedListLRUCache<>(3);
        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        System.out.println(cache);

        cache.put("4","4");
        System.out.println(cache);//结果把1剃除缓存

        cache.get("2");
        System.out.println(cache);//结果把1剃除缓存
    }
}
