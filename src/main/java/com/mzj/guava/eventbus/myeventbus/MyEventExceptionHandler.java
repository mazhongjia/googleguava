package com.mzj.guava.eventbus.myeventbus;

/**
 * EVENT异常处理
 */
public interface MyEventExceptionHandler
{
    void handle(Throwable cause, MyEventContext context);
}
