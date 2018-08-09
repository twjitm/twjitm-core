package com.twjitm.core.common.netstack.sender;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.session.NettySession;

/**
 * @author EGLS0807 - [Created on 2018-08-09 16:35]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 * udp协议消息发送器
 */
public class NettyNetUdpMessageSender implements INetMessageSender {
    private final NettySession nettySession;

    public NettyNetUdpMessageSender(NettySession nettySession) {
        this.nettySession = nettySession;
    }


    @Override
    public boolean sendMessage(AbstractNettyNetMessage abstractNettyNetMessage) {
        try {
            nettySession.write(abstractNettyNetMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void close() {

    }
}
