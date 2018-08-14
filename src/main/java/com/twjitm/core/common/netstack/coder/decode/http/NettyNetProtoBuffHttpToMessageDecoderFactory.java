package com.twjitm.core.common.netstack.coder.decode.http;

import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.netstack.coder.decode.http.INettyNetProtoBuffHttpToMessageDecoderFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetProtoBufMessageBody;
import com.twjitm.core.common.netstack.entity.http.NettyNetHttpMessageHead;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CodecException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by 文江 on 2017/11/16.
 * http message decoder factory
 */
@Service
public class NettyNetProtoBuffHttpToMessageDecoderFactory implements INettyNetProtoBuffHttpToMessageDecoderFactory {
    private Logger logger = LoggerUtils.getLogger(NettyNetProtoBuffHttpToMessageDecoderFactory.class);

    @Override
    public AbstractNettyNetProtoBufMessage parseMessage(ByteBuf byteBuf) {
        //读取head
        NettyNetHttpMessageHead netMessageHead = new NettyNetHttpMessageHead();
        //head为两个字节，跳过
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());

        //读取内容
        short cmd = byteBuf.readShort();
        netMessageHead.setCmd(cmd);
        netMessageHead.setSerial(byteBuf.readInt());

        netMessageHead.setPlayerId(byteBuf.readLong());
        //token
        short tokenLength = byteBuf.readShort();
        byte[] tokenBytes = new byte[tokenLength];
        ByteBuf tokenBuf = byteBuf.readBytes(tokenBytes);
        netMessageHead.setTocken(tokenBuf.toString());

        MessageRegistryFactory messageRegistry = SpringServiceManager.springLoadService.getMessageRegistryFactory();
        AbstractNettyNetProtoBufMessage netMessage = messageRegistry.get(cmd);
        //读取body
        NettyNetProtoBufMessageBody netMessageBody = new NettyNetProtoBufMessageBody();
        int byteLength = byteBuf.readableBytes();
        ByteBuf bodyByteBuffer = null;
        byte[] bytes = new byte[byteLength];
        bodyByteBuffer = byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        netMessageBody.setBytes(bytes);
        netMessage.setNettyNetMessageHead(netMessageHead);
        netMessage.setNettyNetMessageBody(netMessageBody);
        try {
            netMessage.decoderNetProtoBufMessageBody();
            netMessage.releaseMessageBody();
        } catch (Exception e) {
            throw new CodecException("message cmd " + cmd + "decoder error", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("revise net message");
        }
        return netMessage;
    }
}
