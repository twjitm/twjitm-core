package com.twjitm.core.common.factory.thread.async.poll;


import com.twjitm.core.common.factory.thread.async.AsyncCall;

import java.util.concurrent.Future;

/**
 * Created by twjitm on 17/4/19.
 * 异步线程池
 */
public interface AsyncThreadPool {
    public Future submit(AsyncCall asyncCall);
}
