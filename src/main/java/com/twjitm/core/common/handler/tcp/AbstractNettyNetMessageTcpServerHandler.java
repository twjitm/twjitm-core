package com.twjitm.core.common.handler.tcp;

import com.twjitm.core.common.netstack.builder.NettyTcpSessionBuilder;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;


/**
 * @author EGLS0807 - [Created on 2018-07-24 21:07]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public abstract class AbstractNettyNetMessageTcpServerHandler extends ChannelInboundHandlerAdapter {
    Logger logger = LoggerUtils.getLogger(AbstractNettyNetMessageTcpServerHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) SpringServiceManager.springLoadService.getNettyTcpSessionBuilder().buildSession(ctx.channel());
        boolean can =  SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService().addNettySession(nettyTcpSession);
        if (can) {
            AbstractNettyNetMessage errorMessage =  SpringServiceManager.springLoadService.getNettyTcpMessageFactory().createCommonErrorResponseMessage(-1, 10500);
            nettyTcpSession.write(errorMessage);
            nettyTcpSession.close();
            ctx.close();
            return;
        }
        addUpdateSession(nettyTcpSession);
    }

    public abstract void addUpdateSession(NettyTcpSession nettyTcpSession);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof java.io.IOException) {
            return;
        }
        //设置下线
        disconnect(ctx.channel());
        //销毁上下文
        ctx.close();

    }

    /**
     * 下线操作
     *
     * @param channel
     */
    public void disconnect(Channel channel) {
        long sessonId = channel.attr(NettyTcpSessionBuilder.sessionId).get();
        NettyTcpSession nettySession = (NettyTcpSession) SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService().findNettySession(sessonId);
        if (nettySession == null) {
            logger.error("tcp netsession null channelId is:" + channel.id().asLongText());
            return;
        }
        nettySession.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        long sessonId = ctx.channel().attr(NettyTcpSessionBuilder.sessionId).get();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService().findNettySession(sessonId);
        disconnect(ctx.channel());
        if(nettyTcpSession == null){
            ctx.fireChannelUnregistered();
            return;
        }
        SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService().removeNettySession(nettyTcpSession.getSessionId());
        ctx.fireChannelUnregistered();
    }
}
