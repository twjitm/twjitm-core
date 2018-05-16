package com.twjitm;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by 文江 on 2017/11/13.
 */
public class TestChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        //  ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        //ch.pipeline().addLast(new ProtobufEncoder());
        // ch.pipeline().addLast(new RepeatNettyMessageEccoder());
        int maxLength = Integer.MAX_VALUE;
         ch.pipeline().addLast("frame", new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
      //   ch.pipeline().addLast(new NettyNetProtoBufMessageTCPEncoder());
        //ch.pipeline().addLast(new NettyNetProtoBufMessageTCPDecoder());
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        ch.pipeline().addLast(new StringDecoder());
        ch.pipeline().addLast(new StringEncoder());
        ch.pipeline().addLast(new TestClientHandler());
    }


}
