package com.mzj.guava.collections;

import com.google.common.collect.LinkedHashMultimap;
import org.junit.Test;

public class MultimapsExample {

    @Test
    public void test(){
        LinkedHashMultimap<String,String> multimap = LinkedHashMultimap.create();

        multimap.put("1","1");
        multimap.put("1","2");

        System.out.println(multimap.get("1").getClass());
        System.out.println(multimap.get("1"));
    }

}
