package com.twjitm.books.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 客户端引导
 */
public class EchoClient {
    public static void main(String[] args) {
        start(8081);
    }

    private static void start(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group).channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("", port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });
        ChannelFuture future = null;
        try {
            future = bootstrap.connect().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            try {
                group.shutdownGracefully().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
