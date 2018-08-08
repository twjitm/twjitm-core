package com.twjitm.core.start;

import com.twjitm.core.initalizer.NettyUdpMessageServerInitializer;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;

import java.net.InetAddress;

/**
 * @author twjitm - [Created on 2018-07-27 11:29]
 * @jdk java version "1.8.0_77"
 */
public class StartNettyUdpService implements IStartService {
   Logger logger=LoggerUtils.getLogger(StartNettyUdpService.class);
    private int port=9099;
    private String ip="127.0.0.1";
    private static StartNettyUdpService nettyUdpService;

    public static  StartNettyUdpService getInstance(){
        if (nettyUdpService==null){
            return new StartNettyUdpService();
        }
        return nettyUdpService;
    }
    @Override
    public void start() throws Throwable {
        SpringServiceManager.init();
        SpringServiceManager.start();
         start(port);
    }

    @Override
    public void start(int port) throws Throwable {

        Bootstrap b = new Bootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            b.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, false)
                    .option(ChannelOption.SO_REUSEADDR, true) //重用地址
                    .option(ChannelOption.SO_RCVBUF, 65536)
                    .option(ChannelOption.SO_SNDBUF, 65536)
                    .option(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))  // heap buf 's better
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .handler(new NettyUdpMessageServerInitializer());
            ChannelFuture serverChannelFuture = b.bind(ip,port).sync();

            serverChannelFuture.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
            logger.info("---------------------Udp service start is successful  ,ip=["
                    +ip+"Listener port number is :"+port
                    +"]");

        }catch (Exception e){
        }

    }

    @Override
    public void start(InetAddress inetAddress) throws Throwable {

    }

    @Override
    public void stop() throws Throwable {

    }
}
