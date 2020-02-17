package com.mzj.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单listener
 */
public class SimpleListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    /**
     * 通过注解方式告诉eventbus，这是一个listener+参数是String类型的：告诉eventbus，如果有String类型的event，就push给这个listener
     *
     * google guava的eventbus的listener，方法参数只能有一个，多个参数无法识别，如果需要多个参数，可以将参数进行封装
     * @param event
     */
    @Subscribe
    public void doAction(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a action", event);
        }
    }
}
