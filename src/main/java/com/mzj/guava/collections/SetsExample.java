package com.mzj.guava.collections;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetsExample {

    @Test
    public void testCreate(){
        HashSet<Integer> set = Sets.newHashSet(1,2,3,4);
        System.out.println(set.size());

        ArrayList<Integer> integers = Lists.newArrayList(1, 1, 2, 3);
        System.out.println(integers.size());

        HashSet<Integer> integers1 = Sets.newHashSet(integers);
        System.out.println(integers1.size());
    }

    @Test
    public void testCartesianProduct(){
        Set<List<Integer>> set = Sets.cartesianProduct(Sets.newHashSet(1, 2), Sets.newHashSet(3, 4));
        System.out.println(set);
    }

    @Test
    public void testCombinations(){
        Set<Integer> set = Sets.newHashSet(1, 2, 3, 4, 5);
        Set<Set<Integer>> result = Sets.combinations(set, 3);
        result.forEach(System.out::println);
        System.out.println(result);//输出：Sets.combinations([1, 2, 3, 4, 5], 3)，只是toString方法输出的内容，而不是产生的子集实际的内容
    }

    @Test
    public void testPowerSet(){
        Set<Integer> set = Sets.newHashSet(1, 2);
        Set<Set<Integer>> powerSet = Sets.powerSet(set);
        powerSet.forEach(System.out::println);
    }
}
