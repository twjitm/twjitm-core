package com.twjitm.core.start;

import com.twjitm.core.initalizer.WebsocketChatServerInitializer;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by 文江 on 2018/4/16.
 */

public class StartService implements IStartService {
    private static Logger logger = LogManager.getLogManager().getLogger(StartService.class.getName());
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
        EventLoopGroup listenIntoGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup progressGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap(); // (2)
        b.group(listenIntoGroup, progressGroup)
                .channel(NioServerSocketChannel.class) // (3)
                .childHandler(new WebsocketChatServerInitializer()) //(4)
                .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)*/
        // 绑定端口，开始接收进来的连接
        ChannelFuture f = null; // (7)
        try {
            f = b.bind("127.0.0.1", port).sync();
            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            System.out.println("服务器启动了");
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
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
