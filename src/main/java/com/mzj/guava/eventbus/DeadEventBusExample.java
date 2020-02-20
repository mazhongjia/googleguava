package com.mzj.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.mzj.guava.eventbus.listeners.DeadEventListener;

public class DeadEventBusExample {
    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new DeadEventListener());
        eventBus.post("Hello");
        eventBus.post(123);
    }
}
