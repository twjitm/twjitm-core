package com.twjitm.books.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by 文江 on 2018/5/18.
 */
public class EchoServer {
    private static int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new EchoServer(8081).start();
    }

    public static void start() {
        final EchoServiceHandler echoServiceHandler = new EchoServiceHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup listenIntoGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup progressGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap(); // (2)
        b.group(listenIntoGroup, progressGroup)
                .channel(NioServerSocketChannel.class) // (3)
                .childHandler(echoServiceHandler) //(4)
                .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)*/
        // 绑定端口，开始接收进来的连接
        ChannelFuture f = null; // (7)
        try {
            f = b.bind("127.0.0.1", port).sync();
            System.out.println("服务器启动了");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            listenIntoGroup.shutdownGracefully();
            progressGroup.shutdownGracefully();
        }
    }

}
