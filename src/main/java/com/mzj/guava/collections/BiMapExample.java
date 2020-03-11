package com.mzj.guava.collections;

import com.google.common.collect.HashBiMap;
import org.junit.Test;

public class BiMapExample {

    @Test
    public void test(){

        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1","2");
        biMap.put("2","2");
    }

    @Test
    public void test2(){

        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1","2");
        biMap.forcePut("2","2");
        System.out.println(biMap);
    }

}
