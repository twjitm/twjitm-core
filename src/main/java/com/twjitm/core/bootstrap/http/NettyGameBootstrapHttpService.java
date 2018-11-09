package com.twjitm.core.bootstrap.http;

import io.netty.channel.ChannelInitializer;

/**
 * @author twjitm - [Created on 2018-07-27 11:30]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public class NettyGameBootstrapHttpService extends AbstractNettyGameBootstrapHttpService {


    public NettyGameBootstrapHttpService(int serverPort, String serverIp, String bossThreadName, String workThreadName, ChannelInitializer channelInitializer, String serverName) {
        super(serverPort, serverIp, bossThreadName, workThreadName, channelInitializer, serverName);
    }
}
