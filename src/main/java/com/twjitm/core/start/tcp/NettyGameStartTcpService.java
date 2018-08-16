package com.twjitm.core.start.tcp;

import io.netty.channel.ChannelInitializer;

/**
 * @author EGLS0807 - [Created on 2018-08-16 14:10]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class NettyGameStartTcpService extends AbstractNettyGameStartTcpService {

    public NettyGameStartTcpService(int serverPort, String serverIp, String bossTreadName, String workerTreadName, ChannelInitializer channelInitializer) {
        super(serverPort, serverIp, bossTreadName, workerTreadName, channelInitializer);
    }
}
