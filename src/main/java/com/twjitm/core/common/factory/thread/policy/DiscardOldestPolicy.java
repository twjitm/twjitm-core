package com.twjitm.core.common.factory.thread.policy;

import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 10:52
 * https://blog.csdn.net/baidu_23086307
 */
public class DiscardOldestPolicy extends ThreadPoolExecutor.DiscardOldestPolicy{
    private Logger logger= LoggerUtils.getLogger(BlockingPolicy.class);

    private String threadName;

    public DiscardOldestPolicy() {
        this(null);
    }

    public DiscardOldestPolicy(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (threadName != null) {
            logger.error("THREAD POOL "+threadName+" IS EXHAUSTED, EXECUTOR=" + executor.toString());
        }

        super.rejectedExecution(runnable, executor);
    }
}
