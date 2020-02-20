package com.mzj.guava.eventbus.listeners;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class DeadEventListener {

    @Subscribe
    public void handle1(DeadEvent event){
        System.out.println("[Dead listener]"+event.getSource());
        System.out.println("[Dead listener]"+event.getEvent());
    }

    @Subscribe
    public void handle2(String event){
        System.out.println("[String listener]" + event);
    }
}
