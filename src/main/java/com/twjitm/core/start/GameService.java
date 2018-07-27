package com.twjitm.core.start;

/**
 * @author EGLS0807 - [Created on 2018-07-27 11:30]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class GameService {
    public static void main(String[] args) {
        StartNettyTcpService.getInstance().start();
    }


}
