package com.twjitm.core.common.netstack.coder.decode.udp;


import com.twjitm.core.common.netstack.entity.udp.AbstractNettyNetProtoBufUdpMessage;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author twjitm - [Created on 2018-07-30 11:04]
 * udp协议解码器
 *
 * @jdk java version "1.8.0_77"
 */
public class NettyNetProtoBufMessageUDPDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private final Charset charset;
    private INettyNetProtoBuffUDPToMessageDecoderFactory nettyNetProtoBuffUDPToMessageDecoderFactory;

    public NettyNetProtoBufMessageUDPDecoder() {
        this.charset = CharsetUtil.UTF_8;
        nettyNetProtoBuffUDPToMessageDecoderFactory = SpringServiceManager.springLoadService.getNettyNetProtoBuffUDPToMessageDecoderFactory();
    }

    public NettyNetProtoBufMessageUDPDecoder(Charset charset) {
        this.charset = charset;
        nettyNetProtoBuffUDPToMessageDecoderFactory = SpringServiceManager.springLoadService.getNettyNetProtoBuffUDPToMessageDecoderFactory();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        AbstractNettyNetProtoBufUdpMessage abstractNettyNetProtoBufUdpMessage = (AbstractNettyNetProtoBufUdpMessage) nettyNetProtoBuffUDPToMessageDecoderFactory.parseMessage(msg.content());
        abstractNettyNetProtoBufUdpMessage.setSend(msg.sender());
        out.add(abstractNettyNetProtoBufUdpMessage);
    }
}
