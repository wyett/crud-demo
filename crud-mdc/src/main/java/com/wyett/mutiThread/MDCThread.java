package com.wyett.mutiThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author : wyettLei
 * @date : Created in 2020/8/21 11:59
 * @description: TODO
 */

public class MDCThread extends Thread{

    public static final Logger LOGGER = LoggerFactory.getLogger(MDCSample3.class);
    public static final String MEQ_ID = "MEQ_ID";

    private String threadName;

    public MDCThread(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        MDC.put(MEQ_ID, UUID.randomUUID().toString());
        LOGGER.info("Thread " + threadName + " start...");
        LOGGER.info("Thread " + threadName + " end...");
        MDC.remove(MEQ_ID);
    }


}
