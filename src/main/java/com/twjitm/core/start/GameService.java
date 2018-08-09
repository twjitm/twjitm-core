package com.twjitm.core.start;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.factory.thread.TwjThreadFactory;

/**
 * @author EGLS0807 - [Created on 2018-07-27 11:30]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class GameService {
    public static void main(String[] args) {
        TwjThreadFactory factory = new TwjThreadFactory();
        switch (GlobalConstants.ConfigFile.SERVICE_TYPE) {
            case "tcp":
                Thread tcpThread = factory.newThread(() -> StartNettyTcpService.getInstance().start());
                tcpThread.start();
                break;
            case "udp":
                Thread udp = factory.newThread(() -> {
                    try {
                        StartNettyUdpService.getInstance().start();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
                udp.start();
                break;
            default:
                break;
        }
    }


}
