package com.twjitm.core.common.factory.thread;

import java.util.concurrent.ThreadFactory;

/**
 * @author EGLS0807 - [Created on 2018-08-06 21:05]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class ThreadNameFactory implements ThreadFactory {
    private String threadName;

    public ThreadNameFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,threadName);
    }
}
