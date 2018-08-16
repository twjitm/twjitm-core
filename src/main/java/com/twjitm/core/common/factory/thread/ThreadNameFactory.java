package com.twjitm.core.common.factory.thread;

import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author EGLS0807 - [Created on 2018-08-06 21:05]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class ThreadNameFactory implements ThreadFactory {
    Logger logger = LoggerUtils.getLogger(ThreadNameFactory.class);
    private String threadName;
    private ThreadGroup threadGroup;
    private AtomicInteger threadNumber = new AtomicInteger(0);
    private boolean daemon;

    public ThreadNameFactory(String threadName) {
        this(threadName,false);
        this.threadName = threadName;
    }

    public ThreadNameFactory(String threadName, boolean daemon) {
        this.threadName = threadName;
        this.daemon = daemon;
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread()
                .getThreadGroup();
        this.threadName = threadName + "-thread-";
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup, r, threadName
                + threadNumber.getAndIncrement(), 0);
        if (daemon) {
            logger.info(daemon);
            thread.setDaemon(daemon);
        } else {
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
        }
        return thread;
    }
}
