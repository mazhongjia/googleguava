package com.mzj.guava.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.mzj.guava.eventbus.listeners.AsyncSimpleListener;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncEventBusExample
{
    public static void main(String[] args)
    {
        AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(4));

//        AsyncEventBus eventBus = new AsyncEventBus(new SeqExecutor());
        eventBus.register(new AsyncSimpleListener());
        eventBus.post("hello");
    }

    static class SeqExecutor implements Executor
    {

        @Override
        public void execute(Runnable command)
        {
            command.run();
        }
    }
}
