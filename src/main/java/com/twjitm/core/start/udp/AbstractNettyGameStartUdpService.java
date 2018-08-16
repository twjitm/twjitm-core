package com.twjitm.core.start.udp;

import com.twjitm.core.common.factory.thread.ThreadNameFactory;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.start.AbstractNettyGameStartService;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

/**
 * @author twjitm - [Created on 2018-07-27 11:29]
 * @jdk java version "1.8.0_77"
 */
public class AbstractNettyGameStartUdpService extends AbstractNettyGameStartService {
    Logger logger = LoggerUtils.getLogger(AbstractNettyGameStartUdpService.class);

    private int serverPort;
    private String serverIp;

    private EventLoopGroup eventLoopGroup;
    private ChannelFuture serverChannelFuture;

    private ThreadNameFactory eventThreadNameFactory;
    private ChannelInitializer channelInitializer;

    public AbstractNettyGameStartUdpService(int serverPort, String serverIp, String threadName, ChannelInitializer channelInitializer) {
        super(serverPort, new InetSocketAddress(serverIp, serverPort));
        this.serverPort = serverPort;
        this.serverIp = serverIp;
        this.channelInitializer = channelInitializer;
        this.eventThreadNameFactory = new ThreadNameFactory(threadName);
        this.channelInitializer = channelInitializer;

    }


    @Override
    public void startServer() {

        Bootstrap bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(1, eventThreadNameFactory);
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, false)
                    //重用地址
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_RCVBUF, 65536)
                    .option(ChannelOption.SO_SNDBUF, 65536)
                    // heap buf 's better
                    .option(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .handler(channelInitializer);
            serverChannelFuture = bootstrap.bind(serverIp, serverPort).sync();

            serverChannelFuture.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
            logger.info("[---------------------UDP SERVICE START IS SUCCESSFUL IP=[" + serverIp + "]LISTENER PORT NUMBER IS :[" + serverPort + "]------------]");

        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.error("UDP SERVER START HAVE ERROR ", e.getCause());
            }
            SpringServiceManager.shutdown();
        }

    }

    @Override
    public void stopServer() throws Throwable {

    }
}
