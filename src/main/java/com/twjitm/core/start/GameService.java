package com.twjitm.core.start;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.factory.thread.TwjThreadFactory;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

/**
 * @author EGLS0807 - [Created on 2018-07-27 11:30]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class GameService {
    static Logger logger=LoggerUtils.getLogger(GameService.class);
    public static void main(String[] args) {
        fozhu();
        TwjThreadFactory factory = new TwjThreadFactory();
        SpringServiceManager.init();
        SpringServiceManager.start();
        Thread tcpThread = factory.newThread(() -> StartNettyTcpService.getInstance().start());
        tcpThread.start();

        Thread udp = factory.newThread(() -> {
            try {
                StartNettyUdpService.getInstance().start();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        udp.start();
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
                "//           Buddha bless the never down never BUG               //\n");
    }

}
