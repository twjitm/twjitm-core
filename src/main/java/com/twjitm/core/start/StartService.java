package com.twjitm.core.start;

import com.twjitm.core.spring.SpringServiceManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by ÎÄ½­ on 2018/4/16.
 */
public class StartService {
    private static Logger logger = LogManager.getLogManager().getLogger(StartService.class.getName());

    public static void start() {
        SpringServiceManager.init();
        /*EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap(); // (2)*/
        /*b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) // (3)
                .childHandler(new WebsocketChatServerInitializer())  //(4)
                .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)*/
    }

    public static void main(String[] args) {
        start();
    }
}
