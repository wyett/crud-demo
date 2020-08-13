package com.wyett.loadconfig;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author : wyettLei
 * @date : Created in 2020/8/11 9:57
 * @description: TODO
 */

public class LoadConfig {
    public static final String CONFIG_CLASSPATH = "classpath:";
    public static final Logger LOGGER = LoggerFactory.getLogger(LoadConfig.class);

    public static void main(String[] args) throws IOException {
        String conf = System.getProperty("conf.dir", "classpath:test.properties");
        Properties properties = new Properties();
        if (conf.startsWith(CONFIG_CLASSPATH)) {
            conf = StringUtils.substringAfter(conf, CONFIG_CLASSPATH);
            properties.load(LoadConfig.class.getClassLoader().getResourceAsStream(conf));
        } else {
            properties.load(new FileInputStream(conf));
        }

        System.out.println(properties);
    }
}
