package com.mzj.guava.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;

public class ImmutableCollections {

    @Test
    public void testOf(){
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        System.out.println(list);
    }

    @Test
    public void testCopy(){
        Integer[] array = {1,2,3,4,5};
        System.out.println(ImmutableList.copyOf(array));
    }

    @Test
    public void testBuilder(){
        ImmutableList<Integer> list = ImmutableList.<Integer>builder().add(1)
                .add(2, 3, 4)
                .addAll(Arrays.asList(5, 6))
                .build();

        System.out.println(list);
    }

    @Test
    public void testImmutableMap(){
        ImmutableMap<String, String> map = ImmutableMap.of("Java", "1.8").of("Oracle", "12c");
        System.out.println(map);

        ImmutableMap<String, String> map1 = ImmutableMap.<String,String>builder().put("ORACLE","1212").put("ORACLE1","121212").build();
        System.out.println(map1);
    }

}
