package com.twjitm.core.bootstrap;

/**
 * Created by twjitm on 2018/4/17.
 */
public interface INettyBootstrapService {
    public void startServer() throws Throwable;
    void stopServer()throws Throwable;
}
