package com.twjitm.core.common.process;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.tcp.AbstractNettyNetProtoBufTcpMessage;

/**
 * @author EGLS0807 - [Created on 2018-08-06 20:43]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public interface IMessageProcessor {
    /**
     * 启动消息处理器
     */
    public void start();

    /**
     * 停止消息处理器
     */
    public void stop();

    /**
     * 向消息队列投递消息
     *
     * @param msg
     */
    public void put(AbstractNettyNetMessage msg);

    /**
     * 判断队列是否已经达到上限了
     * @return
     */
    public boolean isFull();

}
