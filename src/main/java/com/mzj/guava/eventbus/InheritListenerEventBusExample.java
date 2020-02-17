package com.mzj.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.mzj.guava.eventbus.listeners.ConcreteListener;
import com.mzj.guava.eventbus.listeners.MultipleEventListener;

/**
 * Listener的继承
 */
public class InheritListenerEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new ConcreteListener());

        System.out.println("post the string event...");
        eventBus.post("string event");
    }
}
