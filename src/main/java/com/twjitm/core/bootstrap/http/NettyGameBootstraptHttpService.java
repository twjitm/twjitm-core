package com.twjitm.core.bootstrap.http;

import io.netty.channel.ChannelInitializer;

/**
 * @author EGLS0807 - [Created on 2018-07-27 11:30]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class NettyGameBootstraptHttpService extends AbstractNettyGameBootstraptHttpService {


    public NettyGameBootstraptHttpService(int serverPort, String serverIp, String bossThreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serverPort, serverIp, bossThreadName, workThreadName, channelInitializer);
    }
}
