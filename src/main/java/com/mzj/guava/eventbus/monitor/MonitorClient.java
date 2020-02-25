package com.mzj.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorClient {

    public static void main(String[] args) throws Exception {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());

        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus, "E:\\01.study\\03.google guava\\wamgwenjun\\other\\monitor");

        //monitor.startMonitor();调用会阻塞调用线程，所以只能在单独的一个线程中关闭monitor
        // 延迟异步关闭monitor
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(() -> {
            try {
                monitor.stopMonitor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 20, TimeUnit.SECONDS);
        service.shutdown();
        monitor.startMonitor();
    }
}
