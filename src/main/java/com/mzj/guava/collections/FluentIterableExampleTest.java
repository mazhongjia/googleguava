package com.mzj.guava.collections;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;

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
}
