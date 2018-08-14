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
    public AbstractNettyNetProtoBufMessage parseMessage(ByteBuf byteBuf) {
        NettyNetMessageHead netMessageHead=new NettyUDPMessageHead();
        //跳过2个字节
        byteBuf.skipBytes(2);
        //消息长度
        netMessageHead.setLength(byteBuf.readInt());
        //版本号
        netMessageHead.setVersion(byteBuf.readByte());
        //read message context
        //读取内容
        short cmd = byteBuf.readShort();
        //消息id
        netMessageHead.setCmd(cmd);
        //序列号
        netMessageHead.setSerial(byteBuf.readInt());
        //通过spring管理容器
        MessageRegistryFactory registryFactory =SpringServiceManager.springLoadService.getMessageRegistryFactory();
        AbstractNettyNetProtoBufMessage nettyMessage = registryFactory.get(cmd);
        nettyMessage.setNettyNetMessageHead(netMessageHead);
        NettyNetMessageBody nettyNetMessageBody=new NettyNetMessageBody();
       //数据域大小
        int byteLength = byteBuf.readableBytes();
        ByteBuf bodyByteBuffer = Unpooled.buffer(256);
        byte[] bytes = new byte[byteLength];
        bodyByteBuffer = byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        //保存数据到数据域
        nettyNetMessageBody.setBytes(bytes);
        nettyMessage.setNettyNetMessageBody(nettyNetMessageBody);
        try {
            //提交给具体的消息解码
            nettyMessage.decoderNetProtoBufMessageBody();
        } catch (Exception e) {
            e.printStackTrace();
            nettyMessage.release();
        }
        return nettyMessage;
    }


}
