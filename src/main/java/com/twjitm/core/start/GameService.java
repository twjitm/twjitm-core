package com.twjitm.core.start;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.factory.thread.TwjThreadFactory;
import com.twjitm.core.common.system.SystemService;
import com.twjitm.core.initalizer.NettyHttpMessageServerInitializer;
import com.twjitm.core.initalizer.NettyTcpMessageServerInitializer;
import com.twjitm.core.initalizer.NettyUdpMessageServerInitializer;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.start.http.NettyGameStartHttpService;
import com.twjitm.core.start.tcp.NettyGameStartTcpService;
import com.twjitm.core.start.udp.NettyGameStartUdpService;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

/**
 * @author EGLS0807 - [Created on 2018-07-27 11:30]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 *服务器启动入口
 */
public class GameService {
    static Logger logger = LoggerUtils.getLogger(GameService.class);

    public static void main(String[] args) {
        getBuddha();
        SystemService.getSystem();
        TwjThreadFactory factory = new TwjThreadFactory();
        SpringServiceManager.init();
        SpringServiceManager.start();

        NettyGameStartTcpService tcpService = new NettyGameStartTcpService(
                GlobalConstants.NettyNetServerConfig.TCP.SERVER_PORT,
                GlobalConstants.NettyNetServerConfig.TCP.SERVER_IP,
                GlobalConstants.NettyNetServerConfig.TCP.BOSS_THREAD_NAME,
                GlobalConstants.NettyNetServerConfig.TCP.WORKER_THREAD_NAME,
                new NettyTcpMessageServerInitializer());
        Thread tcpThread = factory.newThread(tcpService::startServer);
        tcpThread.start();

        NettyGameStartUdpService udpService=new NettyGameStartUdpService(
                GlobalConstants.NettyNetServerConfig.UDP.SERVER_PORT,
                GlobalConstants.NettyNetServerConfig.UDP.SERVER_IP,
                GlobalConstants.NettyNetServerConfig.UDP.EVENT_THREAD_NAME,
                new NettyUdpMessageServerInitializer());
        Thread udp = factory.newThread(udpService::startServer);
        udp.start();

        NettyGameStartHttpService httpService=new NettyGameStartHttpService(
                GlobalConstants.NettyNetServerConfig.HTTP.SERVER_PORT,
                GlobalConstants.NettyNetServerConfig.HTTP.SERVER_IP,
                GlobalConstants.NettyNetServerConfig.HTTP.BOSS_THREAD_NAME,
                GlobalConstants.NettyNetServerConfig.HTTP.WORKER_THREAD_NAME,
                new NettyHttpMessageServerInitializer()
        );
        Thread http= factory.newThread(httpService::startServer);
        http.start();
    }

    public static void getBuddha() {
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
                "//           Buddha bless the never down never BUG               //\n");
    }

}
