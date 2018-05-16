package com.twjitm.core.common.netstack.coder.encode;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.buffer.ByteBuf;


public interface INettyNetProtoBufMessageEncoderFactory {
    public ByteBuf createByteBuf(AbstractNettyNetProtoBufMessage netMessage) throws Exception;
}
