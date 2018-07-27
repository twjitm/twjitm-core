package com.twjitm.core.common.netstack.coder.encode.tcp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;


public class NettyNetProtoBufMessageTCPEncoder extends MessageToMessageEncoder<AbstractNettyNetProtoBufMessage> {

    private final Charset charset;

    private INettyNetProtoBufTcpMessageEncoderFactory iNetMessageEncoderFactory;

    public NettyNetProtoBufMessageTCPEncoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageEncoderFactory = SpringServiceManager.springLoadService.getNettyNetProtoBufTcpMessageEncoderFactory();
    }

    public NettyNetProtoBufMessageTCPEncoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractNettyNetProtoBufMessage msg, List<Object> out) throws Exception {
        ByteBuf netMessageBuf = iNetMessageEncoderFactory.createByteBuf(msg);
        out.add(netMessageBuf);
    }
}
