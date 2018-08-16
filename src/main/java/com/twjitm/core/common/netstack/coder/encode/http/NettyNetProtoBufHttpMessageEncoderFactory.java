package com.twjitm.core.common.netstack.coder.encode.http;


import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.common.netstack.entity.http.NettyNetHttpMessageHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class NettyNetProtoBufHttpMessageEncoderFactory implements INettyNetProtoBufHttpMessageEncoderFactory {

    @Override
    public ByteBuf createByteBuf(AbstractNettyNetProtoBufMessage netMessage) throws Exception {
        ByteBuf byteBuf = Unpooled.buffer(256);
        //编写head
        NettyNetHttpMessageHead netMessageHead = (NettyNetHttpMessageHead) netMessage.getNetMessageHead();
        byteBuf.writeShort(netMessageHead.getHead());
        //长度
        byteBuf.writeInt(0);
        //设置内容
        byteBuf.writeByte(netMessageHead.getVersion());
        byteBuf.writeShort(netMessageHead.getCmd());
        byteBuf.writeInt(netMessageHead.getSerial());
        //设置tocken
        byteBuf.writeLong(netMessageHead.getPlayerId());
        byte[] bytes = netMessageHead.getToken().getBytes();
        byteBuf.writeShort(bytes.length);
        byteBuf.writeBytes(bytes);

        //编写body
        netMessage.encodeNetProtoBufMessageBody();
        NettyNetMessageBody netMessageBody = netMessage.getNetMessageBody();
        byteBuf.writeBytes(netMessageBody.getBytes());

        //重新设置长度
        int skip = 6; //short + int
        int length = byteBuf.readableBytes() - skip;
        byteBuf.setInt(2, length);
        byteBuf.slice();
        return byteBuf;
    }
}
