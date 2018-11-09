package com.twjitm.core.common.handler.tcp;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.netstack.builder.NettyTcpSessionBuilder;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;


/**
 * @author twjitm - [Created on 2018-07-24 21:07]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public abstract class AbstractNettyNetMessageTcpServerHandler extends ChannelInboundHandlerAdapter {
    Logger logger = LoggerUtils.getLogger(AbstractNettyNetMessageTcpServerHandler.class);
    private int lossContextNumber = 0;

    /**
     * channel 注册
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) SpringServiceManager.springLoadService.getNettyTcpSessionBuilder().buildSession(ctx.channel());
        boolean can = SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService().addNettySession(nettyTcpSession);
        if (!can) {
            AbstractNettyNetMessage errorMessage = SpringServiceManager.springLoadService.getNettyTcpMessageFactory().createCommonErrorResponseMessage(-1, 10500);
            nettyTcpSession.write(errorMessage);
            nettyTcpSession.close();
            ctx.close();
            return;
        }
        addUpdateSession(nettyTcpSession);
    }

    public abstract void addUpdateSession(NettyTcpSession nettyTcpSession);

    /**
     * have error in project
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
        if (cause instanceof java.io.IOException) {
            logger.error(cause.getMessage());
            //return;
        }
        //  if(logger.isTraceEnabled()){
        logger.error(cause.getMessage(), cause);
        //   }
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
        long sessionId = channel.attr(NettyTcpSessionBuilder.SESSION_ID).get();
        NettyTcpSession nettySession = (NettyTcpSession) SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService().findNettySession(sessionId);
        if (nettySession == null) {
            logger.error("TCP NET SESSION NULL CHANNEL ID IS:" + channel.id().asLongText());
            return;
        }
        nettySession.close();
    }

    /**
     * disconnect; interrupt
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * heart loop check
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        long sessionId = ctx.channel().attr(NettyTcpSessionBuilder.SESSION_ID).get();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case ALL_IDLE:
                    logger.info("SESSION ID =" + sessionId + " IS ALL IDLE");
                    break;
                case READER_IDLE:
                    logger.info("SESSION ID =" + sessionId + " IS READER IDLE");
                    lossContextNumber++;
                    logger.info(lossContextNumber);
                    break;
                case WRITER_IDLE:
                    logger.info("SESSION ID =" + sessionId + " IS READER IDLE");
                    break;
                default:
                    break;
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
        if (lossContextNumber > GlobalConstants.NettyNet.SESSION_HEART_CHECK_NUMBER) {
            NettyTcpSession nettyTcpSession = (NettyTcpSession) SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService().findNettySession(sessionId);
            disconnect(ctx.channel());
            if (nettyTcpSession == null) {
                ctx.fireChannelUnregistered();
                return;
            }
            // remove session
            SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService().removeNettySession(nettyTcpSession.getSessionId());
            ctx.fireChannelUnregistered();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
