package com.mzj.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.mzj.guava.eventbus.events.Apple;
import com.mzj.guava.eventbus.events.Fruit;
import com.mzj.guava.eventbus.listeners.ConcreteListener;
import com.mzj.guava.eventbus.listeners.FruitEateListener;

/**
 * Event的继承
 */
public class InheritEventsEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FruitEateListener());

        eventBus.post(new Apple("apple"));
        System.out.println("--------");
        eventBus.post(new Fruit("fruit"));
    }
}
