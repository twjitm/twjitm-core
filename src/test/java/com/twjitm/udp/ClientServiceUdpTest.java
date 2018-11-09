package com.twjitm.udp;

import com.twjitm.TestSpring;
import com.twjitm.core.common.entity.online.OnlineMessageUdpMessage;
import com.twjitm.core.initalizer.NettyUdpMessageServerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author twjitm - [Created on 2018-08-08 17:19]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 * udp test;
 */
public class ClientServiceUdpTest {
    private static int port=21020;

    public static void main(String[] args) throws InterruptedException {
        TestSpring.initSpring();
        final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioDatagramChannel.class);
        bootstrap.group(nioEventLoopGroup);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
//        bootstrap.handler(new UdpClientChannelInitializer());
        bootstrap.handler(new NettyUdpMessageServerInitializer());
        // 监听端口
        ChannelFuture sync = bootstrap.bind(0).sync();
        Channel udpChannel = sync.channel();

//        sendStringMessage(udpChannel);
        sendUdpMessage(udpChannel);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nioEventLoopGroup.shutdownGracefully()));

        while (true){
            Thread.currentThread().sleep(100l);
        }
    }

    public static void sendUdpMessage(Channel channel) throws InterruptedException {
        InetSocketAddress receive =new InetSocketAddress("127.0.0.1",port);
        OnlineMessageUdpMessage messageUdpMessage=new OnlineMessageUdpMessage();
        messageUdpMessage.setId(520);
        messageUdpMessage.setPlayerId(10086);
        messageUdpMessage.setTocken(520);
        messageUdpMessage.setReceive(receive);
        channel.writeAndFlush(messageUdpMessage).sync();
    }
}
