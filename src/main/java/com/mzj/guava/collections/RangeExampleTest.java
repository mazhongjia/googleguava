package com.mzj.guava.collections;

import com.google.common.collect.BoundType;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import org.junit.Test;

import java.util.NavigableMap;
import java.util.TreeMap;

public class RangeExampleTest {

    /**
     * 逻辑表达式：a<=X<=b
     */
    @Test
    public void testClosedRange(){
        //closed：都包含
        Range<Integer> closeRange = Range.closed(0,9);//参数1为lower的点，参数2为upper的点
        System.out.println(closeRange);
        System.out.println(closeRange.contains(0));
        System.out.println(closeRange.contains(9));

        //lower的点
        System.out.println(closeRange.lowerEndpoint());
        //upper的点
        System.out.println(closeRange.upperEndpoint());

    }

    /**
     * 逻辑表达式：a<X<b
     */
    @Test
    public void testOpenRange(){
        //open：都不包含
        Range<Integer> openRange = Range.open(0,9);//参数1为lower的点，参数2为upper的点
        System.out.println(openRange);
        System.out.println(openRange.contains(0));
        System.out.println(openRange.contains(9));

        //lower的点
        System.out.println(openRange.lowerEndpoint());
        //upper的点
        System.out.println(openRange.upperEndpoint());
    }

    /**
     * 逻辑表达式：a<X<=b
     */
    @Test
    public void testOpenClosedRange(){
        //openClosed：lower不包含，upper包含
        Range<Integer> openRange = Range.openClosed(0,9);//参数1为lower的点，参数2为upper的点
        System.out.println(openRange);
        System.out.println(openRange.contains(0));
        System.out.println(openRange.contains(9));

        //lower的点
        System.out.println(openRange.lowerEndpoint());
        //upper的点
        System.out.println(openRange.upperEndpoint());
    }

    /**
     * 逻辑表达式：a<=X<b
     */
    @Test
    public void testClosedOpenRange(){
        //openClosed：lower包含，upper不包含
        Range<Integer> openRange = Range.closedOpen(0,9);//参数1为lower的点，参数2为upper的点
        System.out.println(openRange);
        System.out.println(openRange.contains(0));
        System.out.println(openRange.contains(9));

        //lower的点
        System.out.println(openRange.lowerEndpoint());
        //upper的点
        System.out.println(openRange.upperEndpoint());
    }

    /**
     * 逻辑表达式：x>a
     */
    @Test
    public void testGreaterThan(){
        Range<Integer> range = Range.greaterThan(10);

        System.out.println(range.contains(10));
        System.out.println(range.contains(Integer.MAX_VALUE));
    }

    /**
     * 基于Map内容的range
     */
    @Test
    public void testMapRange(){
        TreeMap<String,Integer> treeMap = Maps.newTreeMap();

        treeMap.put("Java",1);
        treeMap.put("Guava",2);
        treeMap.put("Netty",3);
        treeMap.put("kafka",4);

        System.out.println(treeMap);//下面的范围获取，是基于放进去后排序的结果进行获取的

        NavigableMap<String, Integer> result = Maps.subMap(treeMap, Range.closed("Java", "Netty"));
        System.out.println(result);
    }

    @Test
    public void testOtherMethod(){
        System.out.println(Range.atLeast(5));
        System.out.println(Range.lessThan(10));
        System.out.println(Range.atMost(10));
        System.out.println(Range.all());
        System.out.println(Range.downTo(10, BoundType.CLOSED));
        System.out.println(Range.upTo(10, BoundType.CLOSED));
    }

}
