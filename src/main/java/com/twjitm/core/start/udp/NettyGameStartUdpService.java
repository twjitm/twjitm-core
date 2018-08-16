package com.twjitm.core.start.udp;

import io.netty.channel.ChannelInitializer;

/**
 * @author EGLS0807 - [Created on 2018-08-16 14:41]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class NettyGameStartUdpService extends AbstractNettyGameStartUdpService{
    public NettyGameStartUdpService(int serverPort, String serverIp, String threadName, ChannelInitializer channelInitializer) {
        super(serverPort, serverIp, threadName, channelInitializer);
    }
}
