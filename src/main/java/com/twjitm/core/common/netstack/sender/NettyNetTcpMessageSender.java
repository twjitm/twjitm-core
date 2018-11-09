package com.twjitm.core.common.netstack.sender;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

/**
 * @author twjitm - [Created on 2018-07-24 16:32]
 * @jdk java version "1.8.0_77"
 * TCP消息发送器
 */
public class NettyNetTcpMessageSender implements INetMessageSender {
    private final NettySession nettySession;
    private Logger logger = LoggerUtils.getLogger(this.getClass());

    public NettyNetTcpMessageSender(NettySession nettySession) {
        this.nettySession = nettySession;
    }

    @Override
    public boolean sendMessage(AbstractNettyNetMessage abstractNettyNetMessage) {
        try {
            nettySession.write(abstractNettyNetMessage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return true;
    }

    @Override
    public void close() {
        Channel channel = nettySession.channel;
        if (channel.isActive()) {
            channel.close();
        } else {
            channel.close();

        }
    }
}
