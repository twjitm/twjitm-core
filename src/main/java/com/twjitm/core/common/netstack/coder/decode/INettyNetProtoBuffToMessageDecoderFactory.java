package com.twjitm.core.common.netstack.coder.decode;


import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by 文江 on 2017/11/16.
 * proto基础消息工厂
 */
public interface INettyNetProtoBuffToMessageDecoderFactory {
    /**
     * handler netty net message
     * @param byteBuf
     * @return
     */
    public AbstractNettyNetProtoBufMessage parseMessage(ByteBuf byteBuf);
}
