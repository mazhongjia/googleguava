package com.mzj.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试异步总线listener
 */
public class AsyncSimpleListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(AsyncSimpleListener.class);

    @Subscribe
    public void doAction1(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a action1", event);
        }
    }

    @Subscribe
    public void doAction2(final String event) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a action2", event);
        }
    }

    @Subscribe
    public void doAction3(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a action3", event);
        }
    }

    @Subscribe
    public void doAction4(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a action4", event);
        }
    }


}
