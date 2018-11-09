package com.twjitm.core.common.handler.tcp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.pipeline.INettyServerPipeLine;
import com.twjitm.core.common.netstack.pipeline.NettyTcpServerPipeLineImpl;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.channel.ChannelHandlerContext;

import javax.annotation.Resource;

/**
 * @author twjitm - [Created on 2018-07-26 21:11]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public class NettyNetMessageTcpServerHandler extends AbstractNettyNetMessageTcpServerHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractNettyNetMessage message = (AbstractNettyNetMessage) msg;
        INettyServerPipeLine nettyTcpServerPipeLine = SpringServiceManager.springLoadService.getNettyTcpServerPipeLine();
        nettyTcpServerPipeLine.dispatch(ctx.channel(), message);
    }

    @Override
    public void addUpdateSession(NettyTcpSession nettyTcpSession) {
    }
}
