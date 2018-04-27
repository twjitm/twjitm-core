package com.twjitm.core.netstack.coder.encode;

import com.twjitm.core.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.buffer.ByteBuf;


public interface INettyNetProtoBufMessageEncoderFactory {
    public ByteBuf createByteBuf(AbstractNettyNetProtoBufMessage netMessage) throws Exception;
}
