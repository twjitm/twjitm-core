package com.twjitm.core.common.handler;

import com.twjitm.core.common.handler.tcp.AbstractNettyNetMessageTcpServerHandler;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by 文江 on 2017/10/25.
 * 心跳协议
 */
public class HeartBeatReqHandler extends AbstractNettyNetMessageTcpServerHandler {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void addUpdateSession(NettyTcpSession nettyTcpSession) {

    }
}
