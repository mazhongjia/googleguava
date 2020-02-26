package com.mzj.guava.eventbus.myeventbus;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * 事件分发
 */
public class MyDispatcher {

    /**
     * 事件分发executor执行器（封装串行还是并行）
     */
    private final Executor executorService;

    private final MyEventExceptionHandler exceptionHandler;

    public static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;

    public static final Executor PRE_THREAD_EXECUTOR_SERVICE = PreThreadExecutorService.INSTANCE;

    private MyDispatcher(Executor executorService, MyEventExceptionHandler exceptionHandler) {
        this.executorService = executorService;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * 分发事件，所需参数均为外部传入，而不是dispatcher持有
     * @param bus
     * @param registry
     * @param event
     * @param topic
     */
    public void dispatch(Bus bus, MyRegistry registry, Object event, String topic) {
        ConcurrentLinkedQueue<MySubscriber> subscribers = registry.scanSubscriber(topic);
        if (null == subscribers) {
            //事件没有感兴趣的listener
            if (exceptionHandler != null) {
                exceptionHandler.handle(new IllegalArgumentException("The topic " + topic + " not bind yet"),
                        new BaseMyEventContext(bus.getBusName(), null, event));
            }

            return;
        }

        subscribers.stream().filter(subscriber -> !subscriber.isDisable())//分发事件时过滤掉unbind的
                .filter(subscriber ->
                {
                    Method subscribeMethod = subscriber.getSubscribeMethod();
                    Class<?> aClass = subscribeMethod.getParameterTypes()[0];
                    return (aClass.isAssignableFrom(event.getClass()));//这里是在关系topic中基础上找到对应的关心的事件类型，isAssignableFrom：判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。如果是则返回 true；否则返回 false。如果该 Class 表示一个基本类型，且指定的 Class 参数正是该 Class 对象，则该方法返回 true；否则返回 false。
                }).forEach(subscriber -> realInvokeSubscribe(subscriber, event, bus));
    }

    private void realInvokeSubscribe(MySubscriber subscriber, Object event, Bus bus) {
        Method subscribeMethod = subscriber.getSubscribeMethod();
        Object subscribeObject = subscriber.getSubscribeObject();
        executorService.execute(() ->
        {
            try {
                subscribeMethod.invoke(subscribeObject, event);
            } catch (Exception e) {
                if (null != exceptionHandler) {
                    exceptionHandler.handle(e, new BaseMyEventContext(bus.getBusName(), subscriber, event));
                }
            }
        });
    }

    public void close() {
        if (executorService instanceof ExecutorService)
            ((ExecutorService) executorService).shutdown();
    }

    static MyDispatcher newDispatcher(MyEventExceptionHandler exceptionHandler, Executor executor) {
        return new MyDispatcher(executor, exceptionHandler);
    }

    static MyDispatcher seqDispatcher(MyEventExceptionHandler exceptionHandler) {
        return new MyDispatcher(SEQ_EXECUTOR_SERVICE, exceptionHandler);
    }

    static MyDispatcher perThreaDDispatcher(MyEventExceptionHandler exceptionHandler) {
        return new MyDispatcher(PRE_THREAD_EXECUTOR_SERVICE, exceptionHandler);
    }

    /**
     * 串行的Executor
     */
    private static class SeqExecutorService implements Executor {

        private final static SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }


    private static class PreThreadExecutorService implements Executor {

        private final static PreThreadExecutorService INSTANCE = new PreThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    private static class BaseMyEventContext implements MyEventContext {

        private final String eventBusName;

        private final MySubscriber subscriber;

        private final Object event;

        private BaseMyEventContext(String eventBusName, MySubscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return this.eventBusName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber != null ? subscriber.getSubscribeObject() : null;
        }

        @Override
        public Method getSubscribe() {
            return subscriber != null ? subscriber.getSubscribeMethod() : null;
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }
}
