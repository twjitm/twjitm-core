package com.twjitm;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by twjitm on 2018/5/14.
 * netty客户端
 */
public class ClientServiceTest {
    static String ip = "127.0.0.1";
    static int port = 11010;

    public static void main(String[] args) {
        TestSpring.initSpring();
            startup(ip,port);
    }


    public static void startup(String ip, int port) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        // bootstrap.bind(ip,port);
        bootstrap.group(eventExecutors);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new TestChannelInitializer());
        try {
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
