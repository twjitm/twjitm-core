package com.twjitm.core.common.netstack.coder.encode;

import com.google.protobuf.MessageLite;
import com.twjitm.core.common.proto.BaseMessageProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by 文江 on 2017/11/16.
 */
public class RepeatNettyMessageEccoder extends MessageToByteEncoder<MessageLite> {
   // HangqingEncoder hangqingEncoder;


    @Override
    protected void encode(
            ChannelHandlerContext ctx, MessageLite msg, ByteBuf out) throws Exception {


        byte[] body = msg.toByteArray();
        byte[] header = encodeHeader(msg, (short)body.length);

        out.writeBytes(header);
        out.writeBytes(body);

        return;
    }

    private byte[] encodeHeader(MessageLite msg, short bodyLength) {
        byte messageType = 0x0f;

        if (msg instanceof BaseMessageProto.BaseMessageProBuf) {
            messageType = 0x00;
        } else if (msg instanceof BaseMessageProto.ChatMessageProBuf) {
            messageType = 0x01;
        }

        byte[] header = new byte[4];
        header[0] = (byte) (bodyLength & 0xff);
        header[1] = (byte) ((bodyLength >> 8) & 0xff);
        header[2] = 0; // 保留字段
        header[3] = messageType;

        return header;

    }
}
