package com.twjitm.core.start.http;

import com.twjitm.core.start.AbstractNettyGameStartService;
import io.netty.channel.ChannelInitializer;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author EGLS0807 - [Created on 2018-07-27 11:30]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class NettyGameStartHttpService extends AbstractNettyGameStartHttpService {


    public NettyGameStartHttpService(int serverPort, String serverIp, String bossThreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serverPort, serverIp, bossThreadName, workThreadName, channelInitializer);
    }
}
