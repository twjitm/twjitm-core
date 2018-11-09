package com.twjitm.core.common.handler.udp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.udp.AbstractNettyNetProtoBufUdpMessage;
import com.twjitm.core.common.netstack.pipeline.INettyServerPipeLine;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 * @author twjitm - [Created on 2018-07-30 11:37]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */

public class NettyNetMessageUdpServerHandler extends AbstractNettyNetMessageUdpServerHandler<AbstractNettyNetProtoBufUdpMessage> {
    Logger logger = Logger.getLogger(NettyNetMessageUdpServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取处理管道
        INettyServerPipeLine udpServerPipeLine = SpringServiceManager.springLoadService.getNettyUdpServerPipeLine();
        udpServerPipeLine.dispatch(ctx.channel(), (AbstractNettyNetMessage) msg);

    }
}
