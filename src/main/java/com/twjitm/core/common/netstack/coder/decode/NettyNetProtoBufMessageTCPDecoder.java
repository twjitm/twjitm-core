package com.twjitm.core.common.netstack.coder.decode;

import com.twjitm.core.spring.SpringServiceManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.jboss.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by 文江 on 2017/11/16.
 */
public class NettyNetProtoBufMessageTCPDecoder  extends MessageToMessageDecoder<ByteBuf>{
          private final Charset charset;
          private INettyNetProtoBuffTCPToMessageDecoderFactory iNettyNetProtoBuffTCPToMessageDecoerFactory;

    public NettyNetProtoBufMessageTCPDecoder() {
        this(CharsetUtil.UTF_8);
    }

    public NettyNetProtoBufMessageTCPDecoder(Class<? extends ByteBuf> inboundMessageType, Charset charset, INettyNetProtoBuffTCPToMessageDecoderFactory iNettyNetProtoBuffTCPToMessageDecoerFactory) {
        super(inboundMessageType);
        this.charset = charset;
        this.iNettyNetProtoBuffTCPToMessageDecoerFactory = iNettyNetProtoBuffTCPToMessageDecoerFactory;
    }

    public NettyNetProtoBufMessageTCPDecoder(Charset charset) {
        if(charset==null)throw new  NullPointerException("charset");
        this.charset=charset;
        iNettyNetProtoBuffTCPToMessageDecoerFactory= SpringServiceManager.springLoadManager.getNettyNetProtoBuffTCPToMessageDecoderFactory();
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if(iNettyNetProtoBuffTCPToMessageDecoerFactory==null){
            System.out.println("iNettyNetProtoBuffTCPToMessageDecoerFactory  is null ");
        }else {
            out.add(iNettyNetProtoBuffTCPToMessageDecoerFactory.praseMessage(msg));

        }
    }
}
