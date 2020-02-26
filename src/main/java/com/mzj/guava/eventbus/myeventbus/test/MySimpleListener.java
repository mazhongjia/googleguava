package com.mzj.guava.eventbus.myeventbus.test;

import com.mzj.guava.eventbus.myeventbus.MySubscribe;

public class MySimpleListener
{

    @MySubscribe
    public void test1(String x)
    {
        System.out.println("MySimpleListener===test1==" + x);
    }

    @MySubscribe(topic = "mazhongjia-topic")
    public void test2(String x)
    {
        System.out.println("MySimpleListener===test2==" + x);
    }

    @MySubscribe(topic = "mazhongjia-topic")
    public void test3(Integer x)
    {
        System.out.println("MySimpleListener===test3==" + x);
    }
}
