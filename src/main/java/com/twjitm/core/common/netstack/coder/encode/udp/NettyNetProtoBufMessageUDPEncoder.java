package com.twjitm.core.common.netstack.coder.encode.udp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.udp.AbstractNettyNetProtoBufUdpMessage;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author twjitm - [Created on 2018-07-30 10:46]
 * UDP协议编码器
 * @jdk java version "1.8.0_77"
 */
public class NettyNetProtoBufMessageUDPEncoder extends MessageToMessageEncoder<AbstractNettyNetProtoBufUdpMessage> {

    private final Charset charset;
    private INettyNetProtoBufUdpMessageEncoderFactory nettyNetProtoBufUdpMessageEncoderFactory;

    public NettyNetProtoBufMessageUDPEncoder() {
        this.charset = CharsetUtil.UTF_8;
        this.nettyNetProtoBufUdpMessageEncoderFactory = SpringServiceManager.springLoadService.getNettyNetProtoBufUdpMessageEncoderFactory();
    }
    public NettyNetProtoBufMessageUDPEncoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException();
        }
        this.charset = charset;
        this.nettyNetProtoBufUdpMessageEncoderFactory = SpringServiceManager.springLoadService.getNettyNetProtoBufUdpMessageEncoderFactory();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractNettyNetProtoBufUdpMessage msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = nettyNetProtoBufUdpMessageEncoderFactory.createByteBuf(msg);
        out.add(new DatagramPacket(byteBuf, msg.getReceive()));
    }

}
