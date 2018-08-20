package com.twjitm.core.initalizer;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.handler.rpc.NettyNetRPCServerHandler;
import com.twjitm.core.common.netstack.coder.decode.rpc.NettyNetMessageRPCDecoder;
import com.twjitm.core.common.netstack.coder.encode.rpc.NettyNetMessageRPCEncoder;
import com.twjitm.core.common.netstack.entity.rpc.NettyRpcRequestMessage;
import com.twjitm.core.common.netstack.entity.rpc.NettyRpcResponseMessage;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 10:19
 * https://blog.csdn.net/baidu_23086307
 */
public class NettyRpcMessageServerInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        ChannelPipeline pipeline = nioSocketChannel.pipeline();
        int maxLength = Integer.MAX_VALUE;
        pipeline.addLast("frame", new LengthFieldBasedFrameDecoder(maxLength, 0, 4, 0, 0));
        pipeline.addLast("decoder", new NettyNetMessageRPCDecoder(NettyRpcRequestMessage.class));
        pipeline.addLast("encoder", new NettyNetMessageRPCEncoder(NettyRpcResponseMessage.class));
        int readerIdleTimeSeconds = 0;
        int writerIdleTimeSeconds = 0;
        int allIdleTimeSeconds = GlobalConstants.NettyNet.SESSION_HEART_ALL_TIMEOUT;
        pipeline.addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
        pipeline.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast("handler", new NettyNetRPCServerHandler());
    }
}
