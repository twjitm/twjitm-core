package com.twjitm.core.common.netstack.session.udp;

import com.twjitm.core.common.netstack.session.NettySession;
import io.netty.channel.Channel;

/**
 * @author EGLS0807 - [Created on 2018-07-24 15:59]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class NettyUdpSession extends NettySession {
    public NettyUdpSession(Channel channel) {
        super(channel);
    }
}
