package com.mzj.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.mzj.guava.eventbus.listeners.MultipleEventListener;
import com.mzj.guava.eventbus.listeners.SimpleListener;

/**
 * 一个类中多个监听回调方法
 */
public class MultipleEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new MultipleEventListener());

        System.out.println("post the string event...");
        eventBus.post("string event");
        System.out.println("post the int event...");
        eventBus.post(123);
    }
}
