package com.twjitm.core.bootstrap;

import java.net.InetSocketAddress;

/**
 * @author EGLS0807 - [Created on 2018-08-16 13:50]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 *
 */
public abstract class AbstractNettyGameBootstrapService implements INettyBootstrapService {
    protected int serverPort;
    protected InetSocketAddress serverAddress;

    public AbstractNettyGameBootstrapService(int serverPort, InetSocketAddress serverAddress) {
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
    }
}
