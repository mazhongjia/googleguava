package com.mzj.guava.collections;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListsExample {

    @Test
    public void test(){
        List<List<String>> result = Lists.cartesianProduct(Lists.newArrayList("1","2"),
                Lists.newArrayList("A","B"));

        System.out.println(result);
    }

    @Test
    public void testTransform(){
        ArrayList<String> sourceList = Lists.newArrayList("Scala","Guava","Lists");
        Lists.transform(sourceList,e->e.toUpperCase()).forEach(System.out::println);
    }

    @Test
    public void testNewArrayListWithCapacity(){
        ArrayList<String> sourceList = Lists.newArrayListWithCapacity(10);
    }

    @Test
    public void testNewArrayListWithExpectedSize(){
        ArrayList<String> sourceList = Lists.newArrayListWithExpectedSize(5);
    }

    @Test
    public void testReverse(){
        ArrayList<String> list = Lists.newArrayList("1","2","3");
        List<String> result = Lists.reverse(list);
        System.out.println(result);
    }

    @Test
    public void testPartition(){
        ArrayList<String> list = Lists.newArrayList("1","2","3","4","5");
        List< List<String>> result = Lists.partition(list,2);//每个分区大小
        System.out.println(result);
    }

}
