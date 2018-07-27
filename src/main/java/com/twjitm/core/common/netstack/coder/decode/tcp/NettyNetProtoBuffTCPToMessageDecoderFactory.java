package com.twjitm.core.common.netstack.coder.decode.tcp;

import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.common.netstack.entity.NettyNetMessageHead;
import com.twjitm.core.common.netstack.entity.udp.NettyUDPMessageHead;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

/**
 * Created by 文江 on 2017/11/16.
 * tcp解码器
 */
@Service
public class NettyNetProtoBuffTCPToMessageDecoderFactory implements INettyNetProtoBuffTCPToMessageDecoderFactory {

    @Override
    public AbstractNettyNetProtoBufMessage praseMessage(ByteBuf byteBuf) {
        NettyNetMessageHead netMessageHead=new NettyUDPMessageHead();
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());
        //read message context
        //读取内容
        short cmd = byteBuf.readShort();
        netMessageHead.setCmd(cmd);
        netMessageHead.setSerial(byteBuf.readInt());
        MessageRegistryFactory registryFactory =SpringServiceManager.springLoadService.getMessageRegistryFactory();
        AbstractNettyNetProtoBufMessage nettyMessage = registryFactory.get(cmd);
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
        }
        return nettyMessage;
    }


}
