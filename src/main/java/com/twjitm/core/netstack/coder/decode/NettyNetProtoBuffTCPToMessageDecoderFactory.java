package com.twjitm.core.netstack.coder.decode;

import com.twjitm.core.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by 文江 on 2017/11/16.
 * tcp解码器
 */
public class NettyNetProtoBuffTCPToMessageDecoderFactory implements INettyNetProtoBuffTCPToMessageDecoerFactory {

    public AbstractNettyNetProtoBufMessage praseMessage(ByteBuf byteBuf) {
        /*NettyNetMessageHead netMessageHead=new NettyUDPMessageHead();
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());
        //read message context
        //读取内容
        short cmd = byteBuf.readShort();
        netMessageHead.setCmd((short) 2);
        netMessageHead.setSerial(byteBuf.readInt());
        MessageRegistryFactory registryFactory = LocalManager.getInstance().getRegistryFactory();
        AbstractNettyNetProtoBufMessage nettyMessage = registryFactory.get(2);
        nettyMessage.setNettyNetMessageHead(netMessageHead);
        NettyNetMessageBody nettyNetMessageBody=new NettyNetMessageBody();

        int byteLength = byteBuf.readableBytes();
        ByteBuf bodyByteBuffer = Unpooled.buffer(256);
        byte[] bytes = new byte[byteLength];
        bodyByteBuffer = byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        nettyNetMessageBody.setBytes(bytes);
        nettyMessage.setNettyNetMessageBody(nettyNetMessageBody);
        try {
            nettyMessage.decoderNetProtoBufMessageBody();
        } catch (Exception e) {
            e.printStackTrace();
            nettyMessage.release();
        }*/
       // return nettyMessage;
        return null;
    }


}
