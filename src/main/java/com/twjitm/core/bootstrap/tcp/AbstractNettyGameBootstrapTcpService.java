package com.twjitm.core.bootstrap.tcp;

import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.bootstrap.AbstractNettyGameBootstrapService;
import com.twjitm.core.utils.logs.LoggerUtils;
import com.twjitm.threads.thread.NettyThreadNameFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;


/**
 * 抽象的tcp协议服务启动类，
 * 本类提供了启动tcp协议服务的抽象类，主要用来实现启动启动服务器，
 * 而具体的操作需要由子类来实现。实现类需要将基本信息传递进来
 * 才可以成功的启动服务。
 * <h3>不要把这个类和{@link  com.twjitm.core.bootstrap.udp.AbstractNettyGameBootstrapUdpService},
 * {@link com.twjitm.core.bootstrap.http.AbstractNettyGameBootstrapHttpService}类相互调用<h3/>
 *
 * <h3>服务器启动过程<h3/>
 * <pre>
 * {@code}
 *     listenIntoGroup = new NioEventLoopGroup(1, bossNettyThreadNameFactory);
 *         progressGroup = new NioEventLoopGroup(0, workerNettyThreadNameFactory);
 *         ServerBootstrap bootstrap = new ServerBootstrap();
 *         bootstrap.group(listenIntoGroup, progressGroup)
 *                 .channel(NioServerSocketChannel.class)
 *                 .childHandler(channelInitializer)
 *                 .option(ChannelOption.SO_BACKLOG, 128)
 *                 .childOption(ChannelOption.SO_KEEPALIVE, true);
 *         ChannelFuture channelFuture;
 *         try {
 *             channelFuture = bootstrap.bind(this.serverIp, this.serverPort).sync();
 *             logger.info("[---------------------" + serverName + " SERVICE START IS SUCCESSFUL IP=[" + serverIp + "]LISTENER PORT NUMBER IS :[" + serverPort + "]------------]");
 *             channelFuture.channel().closeFuture().sync();
 *         } catch (InterruptedException e) {
 *             logger.error(serverName + "START HAVE ERROR ,WILL STOP");
 *             SpringServiceManager.shutdown();
 *             e.printStackTrace();
 *             logger.error(e);
 *         } finally {
 *             listenIntoGroup.shutdownGracefully();
 *             progressGroup.shutdownGracefully();
 *             logger.info(serverName + "SERVER WORLD STOP");
 *         }
 *
 * @author 文江
 * @date 2018/4/16

 */
public abstract class AbstractNettyGameBootstrapTcpService extends AbstractNettyGameBootstrapService {
    private static Logger logger = LoggerUtils.getLogger(AbstractNettyGameBootstrapTcpService.class);
    private int serverPort;
    private String serverIp;

    private String serverName;

    private NettyThreadNameFactory bossNettyThreadNameFactory;
    private NettyThreadNameFactory workerNettyThreadNameFactory;
    private ChannelInitializer channelInitializer;

    private EventLoopGroup listenIntoGroup;
    private EventLoopGroup progressGroup;

    public AbstractNettyGameBootstrapTcpService(int serverPort,
                                                String serverIp,
                                                String bossTreadName,
                                                String workerTreadName,
                                                ChannelInitializer channelInitializer,
                                                String serverName) {
        super(serverPort, new InetSocketAddress(serverIp, serverPort));
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.bossNettyThreadNameFactory = new NettyThreadNameFactory(bossTreadName);
        this.workerNettyThreadNameFactory = new NettyThreadNameFactory(workerTreadName);
        this.channelInitializer = channelInitializer;
        this.serverName = serverName;
    }

    @Override
    public void startServer() {
        listenIntoGroup = new NioEventLoopGroup(1, bossNettyThreadNameFactory);
        progressGroup = new NioEventLoopGroup(0, workerNettyThreadNameFactory);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(listenIntoGroup, progressGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture channelFuture;
        try {
            channelFuture = bootstrap.bind(this.serverIp, this.serverPort).sync();
            logger.info("[---------------------" + serverName + " SERVICE START IS SUCCESSFUL IP=[" + serverIp + "]LISTENER PORT NUMBER IS :[" + serverPort + "]------------]");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(serverName + "START HAVE ERROR ,WILL STOP");
            SpringServiceManager.shutdown();
            e.printStackTrace();
            logger.error(e);
        } finally {
            listenIntoGroup.shutdownGracefully();
            progressGroup.shutdownGracefully();
            logger.info(serverName + "SERVER WORLD STOP");
        }
    }

    @Override
    public void stopServer() throws Throwable {
        listenIntoGroup.shutdownGracefully();
        progressGroup.shutdownGracefully();
    }

}
