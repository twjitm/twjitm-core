package com.twjitm.core.start;

import com.twjitm.core.initalizer.NettyTcpMessageServerInitializer;
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
 *
 * @author 文江
 * @date 2018/4/16
 */

public class StartNettyTcpService implements IStartService {
    private static Logger logger = LoggerUtils.getLogger(StartNettyTcpService.class);
    private int port = 9090;
    private String ip = "127.0.0.1";
    private static StartNettyTcpService startService;

    public static StartNettyTcpService getInstance() {
        if (startService == null) {
            startService = new StartNettyTcpService();
        }
        return startService;
    }

    public StartNettyTcpService() {
        // startService = new StartService();
    }

    @Override
    public void start() {
        fozhu();
        SpringServiceManager.init();
        SpringServiceManager.start();
        EventLoopGroup listenIntoGroup = new NioEventLoopGroup();
        EventLoopGroup progressGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(listenIntoGroup, progressGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyTcpMessageServerInitializer())
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

    @Override
    public void start(int port) throws Throwable {
        this.port = port;
        start();
    }

    @Override
    public void start(InetAddress inetAddress) throws Throwable {
        //  inetAddress.getHostName();
    }

    @Override
    public void stop() throws Throwable {

    }

    public static void fozhu(){
        logger.info("\n" +
                "////////////////////////////////////////////////////////////////////\n" +
                "//                          _ooOoo_                               //\n" +
                "//                         o8888888o                              //\n" +
                "//                         88\" . \"88                              //\n" +
                "//                         (| ^_^ |)                              //\n" +
                "//                         O\\  =  /O                              //\n" +
                "//                      ____/`---'\\____                           //\n" +
                "//                    .'  \\\\|     |//  `.                         //\n" +
                "//                   /  \\\\|||  :  |||//  \\                        //\n" +
                "//                  /  _||||| -:- |||||-  \\                       //\n" +
                "//                  |   | \\\\\\  -  /// |   |                       //\n" +
                "//                  | \\_|  ''\\---/''  |   |                       //\n" +
                "//                  \\  .-\\__  `-`  ___/-. /                       //\n" +
                "//                ___`. .'  /--.--\\  `. . ___                     //\n" +
                "//              .\"\" '<  `.___\\_<|>_/___.'  >'\"\".                  //\n" +
                "//            | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |                 //\n" +
                "//            \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /                 //\n" +
                "//      ========`-.____`-.___\\_____/___.-`____.-'========         //\n" +
                "//                           `=---='                              //\n" +
                "//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //\n" +
                "//            佛祖保佑       永不宕机     永无BUG                 //\n");
    }
}
