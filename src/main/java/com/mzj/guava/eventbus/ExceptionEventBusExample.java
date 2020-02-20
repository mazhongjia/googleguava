package com.mzj.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.mzj.guava.eventbus.listeners.ExceptionListener;

public class ExceptionEventBusExample {
    public static void main(String[] args) {

        final EventBus eventBus = new EventBus(new ExceptionHandler());
        eventBus.register(new ExceptionListener());
        eventBus.post("exception post");
    }

    static class ExceptionHandler implements SubscriberExceptionHandler{

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        }
    }
}
