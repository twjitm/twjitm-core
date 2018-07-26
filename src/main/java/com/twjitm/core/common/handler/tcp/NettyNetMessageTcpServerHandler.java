package com.twjitm.core.common.handler.tcp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import io.netty.channel.ChannelHandlerContext;

import javax.annotation.Resource;

/**
 * @author EGLS0807 - [Created on 2018-07-26 21:11]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class NettyNetMessageTcpServerHandler extends AbstractGameNetMessageTcpServerHandler {
    @Resource
    private IDispatcherService dispatcherService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractNettyNetMessage message = (AbstractNettyNetMessage) msg;
        dispatcherService.dispatcher(message);
    }

    @Override
    public void addUpdateSession(NettyTcpSession nettyTcpSession) {
    }
}
