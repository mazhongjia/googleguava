package com.mzj.guava.eventbus.myeventbus.test;

import com.mzj.guava.eventbus.myeventbus.MyEventBus;

public class MyEventBusExample
{
    public static void main(String[] args)
    {
        MyEventBus myEventBus = new MyEventBus((cause, context) ->
        {
            cause.printStackTrace();
            System.out.println("===================异常处理=======================");
            System.out.println(context.getSource());
            System.out.println(context.getSubscribe());
            System.out.println(context.getEvent());
            System.out.println(context.getSubscriber());
        });
        myEventBus.register(new MySimpleListener());
        myEventBus.register(new MySimpleListener2());

//        myEventBus.post("XXXXXXX");
//        myEventBus.post("111111", "mazhongjia-topic");
//        myEventBus.post(12345, "mazhongjia-topic");
        myEventBus.post(123131, "test-topic");//测试异常


    }
}
