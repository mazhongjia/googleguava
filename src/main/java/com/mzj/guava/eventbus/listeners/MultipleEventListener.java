package com.mzj.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个类中多个监听回调方法
 */
public class MultipleEventListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(MultipleEventListener.class);

    @Subscribe
    public void task1(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a action by ==task1==", event);
        }
    }

    @Subscribe
    public void task2(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a action by ==task2==", event);
        }
    }

    @Subscribe
    public void intTask(final Integer event) {//这里的参数不能时int，而是integer
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a action by ==intTask==", event);
        }
    }
}
