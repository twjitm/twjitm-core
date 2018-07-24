package com.twjitm.core.common.netstack.sender;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;

/**
 * @author twjitm - [Created on 2018-07-24 16:23]
 * 消息处理器
 * @jdk java version "1.8.0_77"
 */
public interface INetMessageSender {

    /**
     * 发送消息
     *
     * @param abstractNettyNetMessage
     * @return
     */
    public boolean sendMessage(AbstractNettyNetMessage abstractNettyNetMessage);

    /**
     * 关闭
     */
    public void close();
}
