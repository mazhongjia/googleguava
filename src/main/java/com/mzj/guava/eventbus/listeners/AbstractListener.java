package com.mzj.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * listener继承
 */
public abstract class AbstractListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(MultipleEventListener.class);

    @Subscribe
    public void commonTask(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Received event [{}] and will take a handle by {}.{}", event,this.getClass().getSimpleName(),"commonTask");
        }
    }
}
