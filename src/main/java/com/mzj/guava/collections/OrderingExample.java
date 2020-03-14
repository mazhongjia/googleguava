package com.mzj.guava.collections;

import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderingExample {

    @Test
    public void testJDKSort(){
        List<Integer> list = Arrays.asList(1,4,5,2,3);
        Collections.sort(list);
        System.out.println(list);
    }

    @Test(expected = NullPointerException.class)
    public void testJDKSortIssue(){
        List<Integer> list = Arrays.asList(1,4,5,null,2,3);
        Collections.sort(list);
        System.out.println(list);//jdk的sort如果遇到null会抛出异常
    }

    /**
     * 如果遇到null，则放在第一个元素
     */
    @Test
    public void testOrderNaturalByNullFirst(){
        List<Integer> list = Arrays.asList(1,4,5,null,2,3);
        Collections.sort(list, Ordering.natural().nullsFirst());
        System.out.println(list);
    }

    /**
     * 如果遇到null，则放在最后一个元素
     */
    @Test
    public void testOrderNaturalByNullLast(){
        List<Integer> list = Arrays.asList(1,4,5,null,2,3);
        Collections.sort(list, Ordering.natural().nullsLast());
        System.out.println(list);
    }

    /**
     * 如果遇到null，则放在最后一个元素
     */
    @Test
    public void testOrderNatural(){
        List<Integer> list = Arrays.asList(1,5,3,8,2);
        Collections.sort(list);
        System.out.println(Ordering.natural().isOrdered(list));//判断当前集合是否是按照自然顺序排序的
    }

}
