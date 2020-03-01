package com.mzj.guava.collections;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FluentIterableExampleTest {

    public FluentIterable<String> build(){
        ArrayList<String> list = Lists.newArrayList("A","BBBB","C","DDD");
        return FluentIterable.from(list);
    }

    @Test
    public void testFilter(){
        FluentIterable<String> lists = build();
        FluentIterable<String> listsResult = lists.filter(e->e!= null && e.length()>1);
        System.out.println(listsResult);
        assertThat(listsResult.size(),equalTo(2));
    }

    @Test
    public void testCycle(){
        FluentIterable<String> lists = build();
        FluentIterable<String> cycle = lists.cycle().limit(20);//cycle是循环，相当于JAVA8的无限流generate
        System.out.println(cycle);
    }

    @Test
    public void testTransformAndConcat(){
        FluentIterable<String> fit = build();
        List<Integer> list = Lists.newArrayList(1,2);

        FluentIterable<Integer> result = fit.transformAndConcat(e ->list);//等价于java8的flatMap效果
        System.out.println(result);
    }
}
