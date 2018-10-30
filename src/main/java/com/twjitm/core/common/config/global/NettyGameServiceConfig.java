package com.twjitm.core.common.config.global;

import com.twjitm.core.common.service.rpc.server.NettySdServer;
import com.twjitm.core.utils.file.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 13:25
 * https://blog.csdn.net/baidu_23086307
 */
@Service
public class NettyGameServiceConfig {
    /**
     * 版本号
     */
    private String serverVersion;
    /**
     * 主机地址
     */
    private String serverHost;
    /**
     * 端口号
     */
    private String serverPort;
    /**
     * 服务器id
     */
    private String serverId;
    /**
     * rpc服务的包名字
     */
    private String rpcServicePackageName;
    /**
     * 异步线程池核心大小
     */
    private int asyncThreadPoolCoreSize;
    /**
     * 异步线程最大大小
     */
    private int asyncThreadPoolMaxSize;
    /**
     * rpc回调超时时间
     */
    private int rpcFutureDeleteTimeOut;
    /**
     * rpc请求超时时间
     */
    private int rpcTimeOut;
    /**
     * rpc连接线程池大小
     */
    private int rpcConnectThreadSize;
    /**
     * rpc连接线程池大小
     */
    private int rpcSendProxyThreadSize;
    /**
     * 开启rpc
     */

    private boolean rpcOpen;
    /**
     * 是否开启zookeeper
     */
    private boolean zookeeperOpen;

    /**
     * 是否开启生命周期任务
     */
    private boolean lifeCycleOpen;
    /**
     * 是否开启kafka组件
     */
    private boolean kakfkaOpen;


    public void init() {
        String config = GlobalConstants.ConfigFile.GAME_SERVER_RPROERTIES_FILE_PATH;
        InputStream inputStream = FileUtil.getFileInputStreanOnSafe(config);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            serverVersion = properties.getProperty("serverVersion");
            serverHost = properties.getProperty("serverHost");
            serverPort = properties.getProperty("serverPort");
            serverId = properties.getProperty("serverId");
            rpcServicePackageName = properties.getProperty("rpcServicePackageName");
            asyncThreadPoolCoreSize = Integer.parseInt(properties.getProperty("asyncThreadPoolCoreSize"));
            asyncThreadPoolMaxSize = Integer.parseInt(properties.getProperty("asyncThreadPoolMaxSize"));
            rpcFutureDeleteTimeOut = Integer.parseInt(properties.getProperty("rpcFutureDeleteTimeOut"));
            rpcTimeOut = Integer.parseInt(properties.getProperty("rpcTimeOut"));
            rpcConnectThreadSize = Integer.parseInt(properties.getProperty("rpcConnectThreadSize"));
            rpcSendProxyThreadSize = Integer.parseInt(properties.getProperty("rpcSendProxyThreadSize"));
            rpcOpen = Boolean.parseBoolean(properties.getProperty("rpcOpen"));
            zookeeperOpen = Boolean.parseBoolean(properties.getProperty("zookeeperOpen"));
            lifeCycleOpen = Boolean.parseBoolean(properties.getProperty("lifeCycleOpen"));
            kakfkaOpen=Boolean.parseBoolean(properties.getProperty("kafkaOpen"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

    public boolean isZookeeperOpen() {
        return zookeeperOpen;
    }

    public void setZookeeperOpen(boolean zookeeperOpen) {
        this.zookeeperOpen = zookeeperOpen;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public boolean isLifeCycleOpen() {
        return lifeCycleOpen;
    }

    public void setLifeCycleOpen(boolean lifeCycleOpen) {
        this.lifeCycleOpen = lifeCycleOpen;
    }

    public boolean isKakfkaOpen() {
        return kakfkaOpen;
    }
}
