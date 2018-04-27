package com.twjitm.core.start;

import java.net.InetAddress;

/**
 * Created by нд╫╜ on 2018/4/17.
 */
public interface IStartService {
    public void start() throws Throwable;
    void start(int port) throws Throwable;
    void start(InetAddress inetAddress) throws Throwable;
    void stop()throws Throwable;
}
