package com.twjitm.core.common.netstack.coder.decode.udp;

import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.common.netstack.entity.udp.AbstractNettyNetProtoBufUdpMessage;
import com.twjitm.core.common.netstack.entity.udp.NettyUDPMessageHead;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.CodecException;
import org.springframework.stereotype.Service;

/**
 * @author twjitm - [Created on 2018-07-30 11:03]
 * udp协议解码工厂
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyNetProtoBuffUDPToMessageDecoderFactory implements INettyNetProtoBuffUDPToMessageDecoderFactory {
    @Override
    public AbstractNettyNetProtoBufMessage parseMessage(ByteBuf byteBuf) {

        //读取head
        NettyUDPMessageHead netMessageHead = new NettyUDPMessageHead();
        //head为两个字节，跳过
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());

        //读取内容
        short cmd = byteBuf.readShort();
        netMessageHead.setCmd(cmd);
        netMessageHead.setSerial(byteBuf.readInt());

        //读取token
        netMessageHead.setPlayerId(byteBuf.readLong());
        netMessageHead.setTocken(byteBuf.readInt());

        MessageRegistryFactory messageRegistry = SpringServiceManager.springLoadService.getMessageRegistryFactory();
        AbstractNettyNetProtoBufUdpMessage netMessage = (AbstractNettyNetProtoBufUdpMessage) messageRegistry.get(cmd);
        //读取body
        NettyNetMessageBody netMessageBody = new NettyNetMessageBody();
        int byteLength = byteBuf.readableBytes();
        ByteBuf bodyByteBuffer = Unpooled.buffer(256);
        byte[] bytes = new byte[byteLength];
        bodyByteBuffer = byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        netMessageBody.setBytes(bytes);
        netMessage.setNettyNetMessageHead(netMessageHead);
        netMessage.setNettyNetMessageBody(netMessageBody);
        try {
            netMessage.decoderNetProtoBufMessageBody();
            netMessage.releaseMessageBody();
        }catch (Exception e){
            throw new CodecException("MESSAGE UDP CMD " + cmd + "DECODER ERROR", e);
        }
        return netMessage;
    }
}
