package com.twjitm.core.start;

import java.net.InetAddress;

/**
 * Created by twjitm on 2018/4/17.
 */
public interface INettyStartService {
    public void startServer() throws Throwable;
    void stopServer()throws Throwable;
}
