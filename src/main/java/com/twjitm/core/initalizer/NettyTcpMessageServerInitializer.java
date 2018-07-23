package com.twjitm.core.initalizer;

import com.twjitm.core.common.netstack.coder.decode.tcp.NettyNetProtoBufMessageTCPDecoder;
import com.twjitm.core.common.netstack.coder.encode.tcp.NettyNetProtoBufMessageTCPEncoder;
import com.twjitm.core.test.TestServiceHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by 文江 on 2017/11/25.
 * TCP协议初始化器
 */
public class NettyTcpMessageServerInitializer extends ChannelInitializer<Channel> {
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 2, 4, 0, 0));
        ch.pipeline().addLast(new NettyNetProtoBufMessageTCPDecoder());
        ch.pipeline().addLast(new NettyNetProtoBufMessageTCPEncoder());
        ch.pipeline().addLast(new TestServiceHandler());

    }
}
