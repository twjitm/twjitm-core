package com.twjitm.core.common.netstack.coder.decode.http;

import com.twjitm.core.common.netstack.coder.decode.http.INettyNetProtoBuffHttpToMessageDecoderFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

/**
 * Created by 文江 on 2017/11/16.
 */
@Service
public class NettyNetProtoBuffHttpToMessageDecoderFactory implements INettyNetProtoBuffHttpToMessageDecoderFactory {
    public AbstractNettyNetProtoBufMessage praseMessage(ByteBuf byteBuf) {
        return null;
    }
}
