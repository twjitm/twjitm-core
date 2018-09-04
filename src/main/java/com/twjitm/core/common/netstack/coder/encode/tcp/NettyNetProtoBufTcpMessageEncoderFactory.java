package com.twjitm.core.common.netstack.coder.encode.tcp;


import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.common.netstack.entity.NettyNetMessageHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;


/**
 * tcp proto buf 协议编码器工厂，主要完成编码功能
 *
 * @author twjitm
 */
@Service
public class NettyNetProtoBufTcpMessageEncoderFactory implements INettyNetProtoBufTcpMessageEncoderFactory {

    @Override
    public ByteBuf createByteBuf(AbstractNettyNetProtoBufMessage netMessage) throws Exception {
        ByteBuf byteBuf = Unpooled.buffer(256);
        //编写head
        NettyNetMessageHead netMessageHead = netMessage.getNetMessageHead();
        byteBuf.writeShort(netMessageHead.getHead());
        //长度
        byteBuf.writeInt(netMessageHead.getLength());
        //设置内容
        byteBuf.writeByte(netMessageHead.getVersion());
        //设置消息id
        byteBuf.writeShort(netMessageHead.getCmd());
        //设置系列号
        byteBuf.writeInt(netMessageHead.getSerial());
        //编写body
        netMessage.encodeNetProtoBufMessageBody();
        NettyNetMessageBody netMessageBody = netMessage.getNetMessageBody();
        byteBuf.writeBytes(netMessageBody.getBytes());

        //重新设置长度
        int skip = 6;
        int length = byteBuf.readableBytes() - skip;
        byteBuf.setInt(2, length);
        //！！！！！！！注意此方法不能调用retain()
        byteBuf.slice();
        return byteBuf;
    }
}

