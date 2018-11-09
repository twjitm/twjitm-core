package com.twjitm.core.common.netstack.coder.encode.udp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.common.netstack.entity.udp.NettyUDPMessageHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

/**
 * @author twjitm - [Created on 2018-07-30 10:46]
 * <p>
 * UPD协议编码工厂
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyNetProtoBufUdpMessageEncoderFactory implements INettyNetProtoBufUdpMessageEncoderFactory {
    @Override
    public ByteBuf createByteBuf(AbstractNettyNetProtoBufMessage netMessage) throws Exception {
        ByteBuf byteBuf = Unpooled.buffer(256);
        //编写head
        NettyUDPMessageHead netMessageHead = (NettyUDPMessageHead) netMessage.getNetMessageHead();
        byteBuf.writeShort(netMessageHead.getHead());
        //长度
        byteBuf.writeInt(0);
        //设置内容
        byteBuf.writeByte(netMessageHead.getVersion());
        byteBuf.writeShort(netMessageHead.getCmd());
        byteBuf.writeInt(netMessageHead.getSerial());
        //设置tockent
        byteBuf.writeLong(netMessageHead.getPlayerId());
        byteBuf.writeInt(netMessageHead.getTocken());

        //编写body
        netMessage.encodeNetProtoBufMessageBody();
        NettyNetMessageBody netMessageBody = netMessage.getNetMessageBody();
        byteBuf.writeBytes(netMessageBody.getBytes());

        //重新设置长度
        int skip = 6;
        int length = byteBuf.readableBytes() - skip;
        byteBuf.setInt(2, length);
        byteBuf.slice();
        return byteBuf;
    }


}
