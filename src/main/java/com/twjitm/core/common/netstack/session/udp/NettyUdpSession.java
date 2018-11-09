package com.twjitm.core.common.netstack.session.udp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.sender.NettyNetUdpMessageSender;
import com.twjitm.core.common.netstack.session.NettySession;
import io.netty.channel.Channel;

/**
 * @author twjitm - [Created on 2018-07-24 15:59]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public class NettyUdpSession extends NettySession {
    private NettyNetUdpMessageSender udpMessageSender;

    public NettyUdpSession(Channel channel) {
        super(channel);
        this.udpMessageSender = new NettyNetUdpMessageSender(this);
    }

    @Override
    public void write(AbstractNettyNetMessage msg) throws Exception {
        if (msg != null) {
            if (channel.isActive()) {
                channel.writeAndFlush(msg);
            }

        }
    }
}
