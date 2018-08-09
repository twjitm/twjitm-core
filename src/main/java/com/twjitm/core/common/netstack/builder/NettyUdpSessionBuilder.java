package com.twjitm.core.common.netstack.builder;

import com.twjitm.core.common.netstack.session.ISession;
import com.twjitm.core.common.netstack.session.udp.NettyUdpSession;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * @author EGLS0807 - [Created on 2018-08-09 16:42]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyUdpSessionBuilder implements ISessionBuilder {

    @Override
    public ISession buildSession(Channel channel) {
        return new NettyUdpSession(channel);
    }
}
