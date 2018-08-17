package com.twjitm.core.initalizer;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.handler.tcp.NettyNetLoggingHandler;
import com.twjitm.core.common.handler.tcp.NettyNetMessageTcpServerHandler;
import com.twjitm.core.common.netstack.coder.decode.tcp.NettyNetProtoBufMessageTCPDecoder;
import com.twjitm.core.common.netstack.coder.encode.tcp.NettyNetProtoBufMessageTCPEncoder;
import com.twjitm.core.test.TestServiceHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by 文江 on 2017/11/25.
 * TCP协议初始化器
 */
public class NettyTcpMessageServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 2, 4, 0, 0));
        ch.pipeline().addLast(new NettyNetProtoBufMessageTCPDecoder());
        ch.pipeline().addLast(new NettyNetProtoBufMessageTCPEncoder());
        //添加心跳檢測
        int readerIdleTimeSeconds = GlobalConstants.NettyNet.SESSION_HEART_READ_TIMEOUT;
        int writerIdleTimeSeconds = GlobalConstants.NettyNet.SESSION_HEART_WRITE_TIMEOUT;
        int allIdleTimeSeconds = GlobalConstants.NettyNet.SESSION_HEART_ALL_TIMEOUT;
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));

        //ch.pipeline().addLast(new TestServiceHandler());
        ch.pipeline().addLast(new NettyNetLoggingHandler(LogLevel.DEBUG));
        ch.pipeline().addLast(new NettyNetMessageTcpServerHandler());
    }
}
