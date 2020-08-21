package com.wyett.mutiThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : wyettLei
 * @date : Created in 2020/8/21 11:51
 * @description: TODO
 */

public class MDCSample3 {


    public static void main(String[] args) {
        new MDCThread("aaa").start();
        new MDCThread("bbb").start();
    }

}
