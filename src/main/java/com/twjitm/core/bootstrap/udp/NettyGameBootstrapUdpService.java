package com.twjitm.core.bootstrap.udp;

import io.netty.channel.ChannelInitializer;

/**
 * @author twjitm - [Created on 2018-08-16 14:41]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public class NettyGameBootstrapUdpService extends AbstractNettyGameBootstrapUdpService {
    public NettyGameBootstrapUdpService(int serverPort, String serverIp, String threadName, ChannelInitializer channelInitializer,String serverName) {
        super(serverPort, serverIp, threadName, channelInitializer,serverName);
    }
}
