package com.mzj.guava.eventbus.myeventbus;

import java.lang.reflect.Method;

/**
 * 处理事件过程中如果遇到异常了，调用MyEventExceptionHandler的handler时，需要传递MyEventContext给应用层
 */
public interface MyEventContext {

    String getSource();

    Object getSubscriber();

    Method getSubscribe();

    Object getEvent();
}
