package com.twjitm.core.common;


import com.twjitm.core.common.initalizer.WebsocketChatServerInitializer;
import com.twjitm.core.common.service.ControllerService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by 文江 on 2017/11/25.
 * http服务器启动类
 * 佛祖保佑，永无bug
 */
public class RealcomWebSocketServer {
    private static RealcomWebSocketServer inItStance;
    public static RealcomWebSocketServer getInItStance() {
        if(inItStance==null){
            inItStance=new RealcomWebSocketServer();
        }
        return inItStance;
    }

    public void startServer() {
      /*  Properties properties=new Properties();
        properties.load();*/
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new WebsocketChatServerInitializer())  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            System.out.println("WebsocketChatServer 启动了");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind("127.0.0.1", 9090).sync(); // (7)
            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("WebsocketChatServer 关闭了");
        }
    }

    public void stopServer() {

    }

    public void initController() {
        ControllerService.init();
    }

    public static void main(String[] args) {
        RealcomWebSocketServer.getInItStance().startServer();
    }

}
