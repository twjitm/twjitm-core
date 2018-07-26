package com.twjitm.core.common.netstack.builder;

import com.twjitm.core.common.netstack.session.ISession;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Service;

/**
 * @author twjitm - [Created on 2018-07-26 20:09]
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyTcpSessionBuilder implements ISessionBuilder {
    public static final AttributeKey<Long> sessionId = AttributeKey.valueOf("sessionId");

    @Override
    public ISession buildSession(Channel channel) {
        NettyTcpSession tcpSession = new NettyTcpSession(channel);
        channel.attr(sessionId).set(tcpSession.getSessionId());
        return tcpSession;
    }
}
