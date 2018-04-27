package com.twjitm.core.netstack.coder.encode;


import com.twjitm.core.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.buffer.ByteBuf;


public class NettyNetProtoBufTcpMessageEncoderFactory implements INettyNetProtoBufTcpMessageEncoderFactory {

    public ByteBuf createByteBuf(AbstractNettyNetProtoBufMessage netMessage) throws Exception {
       /* ByteBuf byteBuf = Unpooled.buffer(256);
        //编写head
        NettyNetMessageHead netMessageHead = netMessage.getNetMessageHead();
        byteBuf.writeShort(netMessageHead.getHead());
        //长度
        byteBuf.writeInt(0);
        //设置内容
        byteBuf.writeByte(netMessageHead.getVersion());
        byteBuf.writeShort(netMessageHead.getCmd());
        byteBuf.writeInt(netMessageHead.getSerial());
        //编写body

        netMessage.encodeNetProtoBufMessageBody();
        NettyNetMessageBody netMessageBody = netMessage.getNetMessageBody();
        byteBuf.writeBytes(netMessageBody.getBytes());

        //重新设置长度
        int skip = 6;
        int length = byteBuf.readableBytes() - skip;
        byteBuf.setInt(2, length);
        byteBuf.slice();
        return byteBuf;*/
       return null;
    }
}

