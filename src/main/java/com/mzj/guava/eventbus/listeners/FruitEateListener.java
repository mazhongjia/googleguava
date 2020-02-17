package com.mzj.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import com.mzj.guava.eventbus.events.Apple;
import com.mzj.guava.eventbus.events.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event的继承
 */
public class FruitEateListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(FruitEateListener.class);

    @Subscribe
    public void eat(Fruit event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fruit eat [{}]", event);
        }
    }

    @Subscribe
    public void eat(Apple event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Apple eat [{}]", event);
        }
    }
}
