package com.twjitm.core.start;

import java.net.InetSocketAddress;

/**
 * @author EGLS0807 - [Created on 2018-08-16 13:50]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 *
 */
public abstract class AbstractNettyGameStartService implements INettyStartService {
    protected int serverPort;
    protected InetSocketAddress serverAddress;

    public AbstractNettyGameStartService(int serverPort, InetSocketAddress serverAddress) {
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
    }
}
