package com.twjitm.core.common.service.rpc.service;

import com.twjitm.core.common.config.global.NettyGameServiceConfig;
import com.twjitm.core.common.config.global.NettyGameServiceConfigService;
import com.twjitm.core.common.factory.thread.NettyRpcHandlerThreadPoolFactory;
import com.twjitm.core.common.service.IService;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.threads.utils.ExecutorUtil;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 10:29
 * https://blog.csdn.net/baidu_23086307
 */
@Service
public class NettyRemoteRpcHandlerService implements IService {

private NettyRpcHandlerThreadPoolFactory rpcHandlerThreadPool;

    @Override
    public String getId() {
        return "NettyRemoteRpcHandlerService";
    }

    @Override
    public void startup() throws Exception {
        NettyGameServiceConfigService config = SpringServiceManager.getSpringLoadService().getNettyGameServiceConfigService();
        NettyGameServiceConfig gameConfig = config.getNettyGameServiceConfig();
        if (gameConfig.isRpcOpen()) {
            //开启服务
            rpcHandlerThreadPool=  SpringServiceManager.getSpringLoadService().
                    getNettyRpcHandlerThreadPoolFactory();
            rpcHandlerThreadPool.createExecutor(
                    gameConfig.getRpcConnectThreadSize(),
                    gameConfig.getRpcSendProxyThreadSize());
        }
    }

    @Override
    public void shutdown() throws Exception {
        ExecutorUtil.shutdownAndAwaitTermination(SpringServiceManager.getSpringLoadService().getNettyRpcHandlerThreadPoolFactory().getExecutor());
    }

    public void submit(Runnable runnable) {
        if(rpcHandlerThreadPool!=null){
            rpcHandlerThreadPool.getExecutor().submit(runnable);
        }
    }
}
