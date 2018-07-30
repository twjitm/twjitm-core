package com.twjitm.core.common.factory.thread;

import java.util.concurrent.ThreadFactory;

/**
 * Created by 文江 on 2017/9/25.
 */
public class TwjThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
