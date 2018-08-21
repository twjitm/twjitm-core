package com.twjitm.core.bootstrap.rpc;

import com.twjitm.core.bootstrap.tcp.AbstractNettyGameBootstrapTcpService;
import io.netty.channel.ChannelInitializer;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 10:03
 * https://blog.csdn.net/baidu_23086307
 */
public class NettyGameBootstrapRpcService extends AbstractNettyGameBootstrapTcpService {

    public NettyGameBootstrapRpcService(int serverPort, String serverIp, String bossTreadName, String workerTreadName, ChannelInitializer channelInitializer,String serverName) {
        super(serverPort, serverIp, bossTreadName, workerTreadName, channelInitializer,serverName);
    }

    @Override
    public void startServer() {
        super.startServer();

    }

    @Override
    public void stopServer() throws Throwable {
        super.stopServer();
    }
}
