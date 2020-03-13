package com.mzj.guava.collections;

import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeMap;
import org.junit.Test;

public class RangeMapExample {

    @Test
    public void testRangeMap(){
        TreeRangeMap<Integer,String> gradeScale = TreeRangeMap.create();
        gradeScale.put(Range.closed(0,60),"E");
        gradeScale.put(Range.closed(61,70),"D");
        gradeScale.put(Range.closed(71,80),"C");
        gradeScale.put(Range.closed(81,90),"B");
        gradeScale.put(Range.closed(91,100),"A");

        System.out.println(gradeScale.get(77));
        System.out.println(gradeScale.get(97));
    }
}
