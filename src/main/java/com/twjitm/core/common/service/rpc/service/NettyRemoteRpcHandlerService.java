package com.twjitm.core.common.service.rpc.service;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.factory.thread.RpcHandlerThreadPoolFactory;
import com.twjitm.core.common.service.IService;
import com.twjitm.core.common.utils.ExecutorUtil;
import com.twjitm.core.spring.SpringServiceManager;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 10:29
 * https://blog.csdn.net/baidu_23086307
 */
@Service
public class NettyRemoteRpcHandlerService implements IService {

private  RpcHandlerThreadPoolFactory rpcHandlerThreadPool;

    @Override
    public String getId() {
        return "NettyRemoteRpcHandlerService";
    }

    @Override
    public void startup() throws Exception {
       boolean isOpenRpc= GlobalConstants.NettyNetServerConfig.RPC.IS_OPEN_RPC;
        if (isOpenRpc) {
            //开启服务
            rpcHandlerThreadPool=  SpringServiceManager.getSpringLoadService().
                    getRpcHandlerThreadPoolFactory();
            rpcHandlerThreadPool.createExecutor(
                            GlobalConstants.NettyNetServerConfig.RPC.RPC_THREAD_POOL_SIZE,
                    GlobalConstants.NettyNetServerConfig.RPC.RPC_THREAD_POOL_QUEUE_SIZE);
        }
    }

    @Override
    public void shutdown() throws Exception {
        ExecutorUtil.shutdownAndAwaitTermination(SpringServiceManager.getSpringLoadService().getRpcHandlerThreadPoolFactory().getExecutor());
    }

    public void submit(Runnable runnable) {
        rpcHandlerThreadPool.getExecutor().submit(runnable);
    }
}
