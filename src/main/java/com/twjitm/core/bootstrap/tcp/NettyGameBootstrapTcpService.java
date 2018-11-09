package com.twjitm.core.bootstrap.tcp;

import io.netty.channel.ChannelInitializer;

/**
 * @author twjitm - [Created on 2018-08-16 14:10]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public class NettyGameBootstrapTcpService extends AbstractNettyGameBootstrapTcpService {

    public NettyGameBootstrapTcpService(int serverPort, String serverIp, String bossTreadName, String workerTreadName, ChannelInitializer channelInitializer,String serverName) {
        super(serverPort, serverIp, bossTreadName, workerTreadName, channelInitializer,serverName);
    }
}
