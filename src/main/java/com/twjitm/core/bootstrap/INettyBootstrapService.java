package com.twjitm.core.bootstrap;

/**
 * Created by twjitm on 2018/4/17.
 * Netty服务启动接口
 * 在服务器加载和启动的时候，调用startServer()方法的具体实现类，既可以启动服。
 * 在服务器启动过程中，我们首先需要加载服务器启动所需要的资源和配置文件{@link com.twjitm.core.spring.SpringServiceManager}
 * 利用spring来管理本地bean对象，通过单列模式的方式加载bean，提高系统资源利用率。
 * 在加载服务器的时候，需要将服务器的信息提交给zookeeper服务器{@link com.twjitm.core.common.zookeeper.NettyZookeeperRpcServiceRegistryService}
 * 同时也为获取其他服务器信息做加载{@link com.twjitm.core.common.zookeeper.NettyZookeeperRpcServiceDiscoveryService}
 *
 */
public interface INettyBootstrapService {
    public void startServer() throws Throwable;
    void stopServer()throws Throwable;
}
