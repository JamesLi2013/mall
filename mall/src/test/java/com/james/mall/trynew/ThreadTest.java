package com.james.mall.trynew;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTest {
    private  static  Logger logger= LoggerFactory.getLogger(ThreadTest.class);
    public static void main(String[] args){
        logger.warn("main");
        startNewThread(1);
        startNewThread(2);
        startNewThread(3);
    }

    public static void startNewThread(final int number){
        new Thread(){
            @Override
            public void run() {
                super.run();
                logger.warn("thread:"+number);
            }
        }.start();
    }
}
