package com.twjitm.core.common.factory.thread.async.poll;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.config.global.NettyGameServiceConfig;
import com.twjitm.core.common.config.global.NettyGameServiceConfigService;
import com.twjitm.core.common.factory.thread.async.AsyncCall;
import com.twjitm.core.common.service.IService;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.threads.thread.NettyThreadNameFactory;
import com.twjitm.threads.utils.ExecutorUtil;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Created by twjitm on 17/4/19.
 * 增加异步线程池
 */
@Service
public class AsyncThreadService implements AsyncThreadPool, IService {

    protected ExecutorService executorService;


    @Override
    public Future submit(AsyncCall asyncCall) {
        return executorService.submit(asyncCall);
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public void startup() throws Exception {
        NettyGameServiceConfigService gameServerConfigService = SpringServiceManager.getSpringLoadService().getNettyGameServiceConfigService();
        NettyGameServiceConfig gameServerConfig = gameServerConfigService.getNettyGameServiceConfig();
        NettyThreadNameFactory nettyThreadNameFactory =  new NettyThreadNameFactory(GlobalConstants.Thread.GAME_ASYNC_CALL);
        executorService = new ThreadPoolExecutor(gameServerConfig.getAsyncThreadPoolCoreSize(), gameServerConfig.getAsyncThreadPoolMaxSize(),60L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(), nettyThreadNameFactory);
    }

    @Override
    public void shutdown() throws Exception {
        ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 60, TimeUnit.SECONDS);
    }
}
