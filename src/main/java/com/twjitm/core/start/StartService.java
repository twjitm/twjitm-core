package com.twjitm.core.start;

import com.twjitm.core.initalizer.WebsocketChatServerInitializer;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

import java.net.InetAddress;


/**
 * Created by 文江 on 2018/4/16.
 */

public class StartService implements IStartService {
    private static Logger logger = LoggerUtils.getLogger(StartService.class);
    private int port = 9090;
    private String ip = "127.0.0.1";
    private static StartService startService;

    public static StartService getInstance() {
        if (startService == null) {
            startService = new StartService();
        }
        return startService;
    }

    public StartService() {
        // startService = new StartService();
    }

    public void start() {
        SpringServiceManager.init();
        EventLoopGroup listenIntoGroup = new NioEventLoopGroup();
        EventLoopGroup progressGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(listenIntoGroup, progressGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebsocketChatServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture f;
        try {
            f = b.bind(ip, port).sync();
            logger.info("服务器启动成功,监听端口" + port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("服务器启动失败");
            e.printStackTrace();
            logger.error(e);
        } finally {
            listenIntoGroup.shutdownGracefully();
            progressGroup.shutdownGracefully();
            System.out.println("WebsocketChatServer 关闭了");
        }

    }

    public void start(int port) throws Throwable {
        this.port = port;
        start();
    }

    public void start(InetAddress inetAddress) throws Throwable {
        //  inetAddress.getHostName();
    }

    public void stop() throws Throwable {

    }

    public static void main(String[] args) {
        StartService.getInstance().start();
    }
}
