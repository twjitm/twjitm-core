package com.twjitm.core.netstack.coder.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by 文江 on 2017/11/14.
 */
public class NettyNetProtoBufToMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(msg);
    }
}
