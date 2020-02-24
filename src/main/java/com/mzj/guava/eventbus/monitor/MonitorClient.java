package com.mzj.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;

public class MonitorClient {

    public static void main(String[] args) throws Exception {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());

        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus,"E:\\01.study\\03.google guava\\wamgwenjun\\other\\monitor");

        monitor.startMonitor();
    }
}
