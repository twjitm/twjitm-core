package com.twjitm.core.initalizer;


import com.twjitm.core.common.netstack.coder.decode.NettyNetProtoBufMessageTCPDecoder;
import com.twjitm.core.common.netstack.coder.decode.RepeatNettyMessageDecoder;
import com.twjitm.core.common.netstack.coder.encode.NettyNetProtoBufMessageTCPEncoder;
import com.twjitm.core.test.TestServiceHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by 文江 on 2017/9/25.
 */
public class WebsocketChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {//2
        ChannelPipeline pipeline = ch.pipeline();

        /*pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChunkedWriteHandler());*/
        //请求handler
        // pipeline.addLast(new HttpRequestHandler("/ws"));
       // pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
      //  pipeline.addLast(new NettyNetProtoBufMessageTCPDecoder());
        pipeline.addLast(new RepeatNettyMessageDecoder());
       pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        //pipeline.addLast(new ProtobufVarint32FrameDecoder());
       pipeline.addLast(new RepeatNettyMessageDecoder());
        pipeline.addLast(new NettyNetProtoBufMessageTCPDecoder());
        pipeline.addLast(new NettyNetProtoBufMessageTCPEncoder());
        //pipeline.addLast(new NettyCommonSessionWebSocketHandler());
        pipeline.addLast(new TestServiceHandler());
    }
}
