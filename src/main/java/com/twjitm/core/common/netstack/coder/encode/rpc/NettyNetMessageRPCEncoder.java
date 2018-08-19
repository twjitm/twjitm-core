package com.twjitm.core.common.netstack.coder.encode.rpc;

import com.twjitm.core.common.service.rpc.serialize.NettyProtoBufRpcSerialize;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 9:55
 * https://blog.csdn.net/baidu_23086307
 */
public class NettyNetMessageRPCEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public NettyNetMessageRPCEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            NettyProtoBufRpcSerialize serialize = SpringServiceManager.getSpringLoadService().getNettyProtoBufRpcSerialize();
            byte[] data = serialize.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
