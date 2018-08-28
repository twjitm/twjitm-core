package com.twjitm.core.common.netstack.coder.decode.tcp;

import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author tjwitm
 * @date 2017/11/16
 */
public class NettyNetProtoBufMessageTCPDecoder extends MessageToMessageDecoder<ByteBuf> {
    Logger logger = LoggerUtils.getLogger(NettyNetProtoBufMessageTCPDecoder.class);
    private final Charset charset;
    private INettyNetProtoBuffTCPToMessageDecoderFactory iNettyNetProtoBuffTCPToMessageDecoderFactory;

    public NettyNetProtoBufMessageTCPDecoder() {
        this(CharsetUtil.UTF_8);
    }

    public NettyNetProtoBufMessageTCPDecoder(Class<? extends ByteBuf> inboundMessageType, Charset charset, INettyNetProtoBuffTCPToMessageDecoderFactory iNettyNetProtoBuffTCPToMessageDecoerFactory) {
        super(inboundMessageType);
        this.charset = charset;
        this.iNettyNetProtoBuffTCPToMessageDecoderFactory = iNettyNetProtoBuffTCPToMessageDecoerFactory;
    }

    public NettyNetProtoBufMessageTCPDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
        iNettyNetProtoBuffTCPToMessageDecoderFactory = SpringServiceManager.springLoadService.getNettyNetProtoBuffTCPToMessageDecoderFactory();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if (iNettyNetProtoBuffTCPToMessageDecoderFactory == null) {
            logger.error("iNettyNetProtoBuffTCPToMessageDecoderFactory  is null ");
        } else {
            out.add(iNettyNetProtoBuffTCPToMessageDecoderFactory.parseMessage(msg));

        }
    }
}
