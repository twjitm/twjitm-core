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
    /**
     * 通过netty 提供的属性保存机制，提供在{@link Channel}中保存一些自定义数据的容器。
     * {@link AttributeKey}
     */
    public static final AttributeKey<Long> SESSION_ID = AttributeKey.valueOf("SESSION_ID");

    @Override
    public ISession buildSession(Channel channel) {
        NettyTcpSession tcpSession = new NettyTcpSession(channel);
        channel.attr(SESSION_ID).set(tcpSession.getSessionId());
        return tcpSession;
    }
}
