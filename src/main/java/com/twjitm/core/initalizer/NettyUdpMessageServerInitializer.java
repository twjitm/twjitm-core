package com.twjitm.core.initalizer;

import com.twjitm.core.common.handler.udp.NettyNetMessageUdpServerHandler;
import com.twjitm.core.common.netstack.coder.decode.udp.NettyNetProtoBufMessageUDPDecoder;
import com.twjitm.core.common.netstack.coder.encode.udp.NettyNetProtoBufMessageUDPEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * udp协议初始化器
 */
public class NettyUdpMessageServerInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline channelPipLine = ch.pipeline();
        int maxLength = Integer.MAX_VALUE;
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        ch.pipeline().addLast(new NettyNetProtoBufMessageUDPEncoder());
        ch.pipeline().addLast(new NettyNetProtoBufMessageUDPDecoder());
        channelPipLine.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        channelPipLine.addLast(new NettyNetMessageUdpServerHandler());


    }
}
