package com.mzj.guava.eventbus.myeventbus.test;

import com.mzj.guava.eventbus.myeventbus.MySubscribe;

import java.util.concurrent.TimeUnit;

public class MySimpleListener2
{

    @MySubscribe
    public void test1(String x)
    {
        System.out.println("MySimpleListener2===test1==" + x);
    }

    @MySubscribe(topic = "mazhongjia-topic")
    public void test2(Integer x)
    {
        try
        {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("MySimpleListener2===test2==" + x);
    }

    @MySubscribe(topic = "test-topic")
    public void test3(Integer x)
    {
        throw new RuntimeException();
    }
}
