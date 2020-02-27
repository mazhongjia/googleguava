package com.mzj.guava.eventbus.myeventbus.test;

import com.mzj.guava.eventbus.myeventbus.MyAsyncEventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MyAsyncBusExample
{

    public static void main(String[] args)
    {
        MyAsyncEventBus eventBus = new MyAsyncEventBus((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
        eventBus.register(new MySimpleListener());
        eventBus.register(new MySimpleListener2());
        eventBus.post(123131, "alex-topic");
        eventBus.post("hello");

    }
}
