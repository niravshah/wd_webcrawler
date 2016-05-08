package com.nns.wd.services.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Nirav on 09/05/2016.
 */
public class RedisRequestReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRequestReceiver.class);

    private CountDownLatch latch;

    @Autowired
    BeanFactory factory;

    @Autowired
    public RedisRequestReceiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");

        latch.countDown();
    }
}
