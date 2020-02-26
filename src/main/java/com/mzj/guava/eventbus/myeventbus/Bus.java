package com.mzj.guava.eventbus.myeventbus;

/**
 * 自定义eventbus接口
 */
public interface Bus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    /**
     * 无topic发送event接口
     *
     * @param event
     */
    void post(Object event);

    /**
     * 有topic发送event接口
     *
     * @param Event
     * @param topic
     */
    void post(Object Event, String topic);

    void close();

    String getBusName();
}
