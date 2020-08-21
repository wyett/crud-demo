package com.wyett.simpleSamples;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author : wyettLei
 * @date : Created in 2020/8/21 10:50
 * @description: TODO
 */

public class MDCSample1 {

    public static final Logger LOG = LoggerFactory.getLogger(MDCSample1.class);

    public static void main(String[] args) {
        MDC.put("THREAD_ID", String.valueOf(Thread.currentThread().getId()));
        LOG.info("hello, wyett");
    }
}
