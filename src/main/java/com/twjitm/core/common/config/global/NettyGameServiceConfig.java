package com.twjitm.core.common.config.global;

import com.twjitm.core.common.service.rpc.server.NettySdServer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 13:25
 * https://blog.csdn.net/baidu_23086307
 */
@Service
public class NettyGameServiceConfig {
    /**
     * rpc服务的包名字
     */
    private String rpcServicePackageName = "com.twjitm.core.service.rpc.service";
    private int asyncThreadPoolCoreSize = 5;
    private int asyncThreadPoolMaxSize = 5;
    private int rpcFutureDeleteTimeOut = 2000;
    private int rpcTimeOut = 5000;
    /*rpc连接线程池大小*/
    private int rpcConnectThreadSize=5;
    /*rpc连接线程池大小*/
    private int rpcSendProxyThreadSize=5;
    //开启rpc
    private boolean rpcOpen = false;


    public void setRpcServicePackageName(String rpcServicePackageName) {
        this.rpcServicePackageName = rpcServicePackageName;
    }

    public String getRpcServicePackageName() {
        return rpcServicePackageName;
    }

    public int getAsyncThreadPoolCoreSize() {
        return asyncThreadPoolCoreSize;
    }

    public void setAsyncThreadPoolCoreSize(int asyncThreadPoolCoreSize) {
        this.asyncThreadPoolCoreSize = asyncThreadPoolCoreSize;
    }

    public int getAsyncThreadPoolMaxSize() {
        return asyncThreadPoolMaxSize;
    }

    public void setAsyncThreadPoolMaxSize(int asyncThreadPoolMaxSize) {
        this.asyncThreadPoolMaxSize = asyncThreadPoolMaxSize;
    }

    public boolean isRpcOpen() {
        return rpcOpen;
    }

    public void setRpcOpen(boolean rpcOpen) {
        this.rpcOpen = rpcOpen;
    }

    public int getRpcFutureDeleteTimeOut() {
        return rpcFutureDeleteTimeOut;
    }

    public void setRpcFutureDeleteTimeOut(int rpcFutureDeleteTimeOut) {
        this.rpcFutureDeleteTimeOut = rpcFutureDeleteTimeOut;
    }

    public int getRpcTimeOut() {
        return rpcTimeOut;
    }

    public void setRpcTimeOut(int rpcTimeOut) {
        this.rpcTimeOut = rpcTimeOut;
    }

    public int getRpcConnectThreadSize() {
        return rpcConnectThreadSize;
    }

    public void setRpcConnectThreadSize(int rpcConnectThreadSize) {
        this.rpcConnectThreadSize = rpcConnectThreadSize;
    }

    public int getRpcSendProxyThreadSize() {
        return rpcSendProxyThreadSize;
    }

    public void setRpcSendProxyThreadSize(int rpcSendProxyThreadSize) {
        this.rpcSendProxyThreadSize = rpcSendProxyThreadSize;
    }


}
