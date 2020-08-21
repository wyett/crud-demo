package com.wyett.simpleSamples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author : wyettLei
 * @date : Created in 2020/8/21 11:19
 * @description: TODO
 */

public class MDCSample2 {
    public static final Logger LOGGER = LoggerFactory.getLogger(MDCSample2.class);
    public static final String MEQ_ID = "MEQ_ID";

    public static void main(String[] args) {
        MDC.put(MEQ_ID, UUID.randomUUID().toString());
        LOGGER.info("good morning");
        LOGGER.info("long time no see");
        MDC.remove(MEQ_ID);
        LOGGER.info("good evening");
    }
}
