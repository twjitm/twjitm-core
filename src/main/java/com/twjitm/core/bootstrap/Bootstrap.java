package com.twjitm.core.bootstrap;

import com.twjitm.core.bootstrap.http.NettyGameBootstrapHttpService;
import com.twjitm.core.bootstrap.rpc.NettyGameBootstrapRpcService;
import com.twjitm.core.bootstrap.tcp.NettyGameBootstrapTcpService;
import com.twjitm.core.bootstrap.udp.NettyGameBootstrapUdpService;
import com.twjitm.core.common.config.global.*;
import com.twjitm.core.common.system.SystemService;
import com.twjitm.core.initalizer.NettyHttpMessageServerInitializer;
import com.twjitm.core.initalizer.NettyRpcMessageServerInitializer;
import com.twjitm.core.initalizer.NettyTcpMessageServerInitializer;
import com.twjitm.core.initalizer.NettyUdpMessageServerInitializer;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.test.AsyncExecutorService;
import com.twjitm.core.utils.logs.LoggerUtils;
import com.twjitm.threads.thread.NettyThreadNameFactory;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author twjitm - [Created on 2018-07-27 11:30]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 * 服务器启动入口
 */
public class Bootstrap {
    static Logger logger = LoggerUtils.getLogger(Bootstrap.class);
    static org.slf4j.Logger loggers = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        getBuddha();

        SystemService.getSystem();
        Bootstrap.init();
        Bootstrap.startServer();
        AsyncExecutorService asyncExecutorService = SpringServiceManager.getSpringLoadService().getAsyncExecutorService();
        asyncExecutorService.saveUser(null);
    }

    public static void init() {
        loggers.info("INIT SERVER STARTING");
        SpringServiceManager.init();
        SpringServiceManager.start();
    }

    public static void startServer() {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new
                NettyThreadNameFactory("bootstrap_service_thread_"));

        NettyGameServiceConfig config = SpringServiceManager.getSpringLoadService().getNettyGameServiceConfigService().getNettyGameServiceConfig();
        int tcpPort = Integer.parseInt(config.getServerPort());
        NettyGameBootstrapTcpService tcpService = new NettyGameBootstrapTcpService(
                tcpPort,
                config.getServerHost(),
                GlobalConstants.NettyNetServerConfig.TCP.BOSS_THREAD_NAME,
                GlobalConstants.NettyNetServerConfig.TCP.WORKER_THREAD_NAME,
                new NettyTcpMessageServerInitializer(),
                GlobalConstants.NettyNetServerConfig.TCP.SERVER_NAME);
        executorService.execute(tcpService::startServer);

        NettyGameUdpConfig udpConfig = SpringServiceManager.getSpringLoadService().getNettyGameServiceConfigService().getUdpConfig();
        NettyGameBootstrapUdpService udpService = new NettyGameBootstrapUdpService(
                udpConfig.getServerPort(),
                udpConfig.getServerHost(),
                GlobalConstants.NettyNetServerConfig.UDP.EVENT_THREAD_NAME,
                new NettyUdpMessageServerInitializer(),
                GlobalConstants.NettyNetServerConfig.UDP.SERVER_NAME);
        executorService.execute(udpService::startServer);

        NettyGameHttpConfig httpConfig = SpringServiceManager.getSpringLoadService().getNettyGameServiceConfigService().getHttpConfig();
        NettyGameBootstrapHttpService httpService = new NettyGameBootstrapHttpService(
                httpConfig.getServerPort(),
                httpConfig.getServerHost(),
                GlobalConstants.NettyNetServerConfig.HTTP.BOSS_THREAD_NAME,
                GlobalConstants.NettyNetServerConfig.HTTP.WORKER_THREAD_NAME,
                new NettyHttpMessageServerInitializer(),
                GlobalConstants.NettyNetServerConfig.HTTP.SERVER_NAME
        );
        executorService.execute(httpService::startServer);

        NettyGameRpcConfig rpcConfig = SpringServiceManager.getSpringLoadService().getNettyGameServiceConfigService().getRpcNetConfig();
        NettyGameBootstrapRpcService rpcService = new NettyGameBootstrapRpcService(
                rpcConfig.getServerPort(),
                rpcConfig.getServerHost(),
                GlobalConstants.NettyNetServerConfig.RPC.BOSS_THREAD_NAME,
                GlobalConstants.NettyNetServerConfig.RPC.WORKER_THREAD_NAME,
                new NettyRpcMessageServerInitializer(),
                GlobalConstants.NettyNetServerConfig.RPC.SERVER_NAME);
        executorService.execute(rpcService::startServer);

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
