package com.mzj.guava.cache.lru.linkedhashmap;

import com.mzj.guava.cache.lru.LRUCache;

public class LinkedHashMapLRUCacheExample {
    public static void main(String[] args) {
        LRUCache<String,String> cache = new LinkedHashMapLRUCache<>(3);
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
