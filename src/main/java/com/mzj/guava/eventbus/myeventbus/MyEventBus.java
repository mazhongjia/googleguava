package com.mzj.guava.eventbus.myeventbus;

import java.util.concurrent.Executor;

/**
 * 自定义eventbus实现
 */
public class MyEventBus implements Bus {

    /**
     * 保存registry到总线上的listener信息
     */
    private final MyRegistry registry = new MyRegistry();

    private String busName;

    private final static String DEFAULT_BUS_NAME = "default";

    private final static String DEFAULT_TOPIC = "default-topic";

//    private final MyEventExceptionHandler exceptionHandler;//eventbus中不需要异常处理handler，因为直接将handler传给了dispatcher，EventBus相当于facade

    private final MyDispatcher dispatcher;

    public MyEventBus() {
        this(DEFAULT_BUS_NAME, null, MyDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public MyEventBus(String busName) {
        this(busName, null, MyDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    /**
     * 此构造函数设计成包级，目的是指定Executor executor的构造函数不允许外部创建，而是通过包内提供的MyAsyncEventBus实现
     * @param busName
     * @param exceptionHandler
     * @param executor
     */
    MyEventBus(String busName, MyEventExceptionHandler exceptionHandler, Executor executor) {
        this.busName = busName;
        this.dispatcher = MyDispatcher.newDispatcher(exceptionHandler, executor);
    }

    public MyEventBus(MyEventExceptionHandler exceptionHandler) {
        this(DEFAULT_BUS_NAME, exceptionHandler, MyDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
