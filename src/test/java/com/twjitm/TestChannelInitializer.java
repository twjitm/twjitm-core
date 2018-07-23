package com.twjitm;

import com.twjitm.core.common.netstack.coder.decode.tcp.NettyNetProtoBufMessageTCPDecoder;
import com.twjitm.core.common.netstack.coder.encode.tcp.NettyNetProtoBufMessageTCPEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;


/**
 * Created by 文江 on 2017/11/13.
 */
public class TestChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {

        int maxLength = Integer.MAX_VALUE;
        ch.pipeline().addLast("frame", new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        ch.pipeline().addLast(new NettyNetProtoBufMessageTCPEncoder());
        ch.pipeline().addLast(new NettyNetProtoBufMessageTCPDecoder());
        ch.pipeline().addLast(new TestClientHandler());
    }


    /**
     * google proto buffer  encode and decode;
     */

 /*   @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        // ----Protobuf处理器，这里的配置是关键----
        pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());// 用于decode前解决半包和粘包问题（利用包头中的包含数组长度来识别半包粘包）
        //配置Protobuf解码处理器，消息接收到了就会自动解码，ProtobufDecoder是netty自带的，Message是自己定义的Protobuf类
        pipeline.addLast("protobufDecoder",
        new ProtobufDecoder();
        // 用于在序列化的字节数组前加上一个简单的包头，只包含序列化的字节长度。
        pipeline.addLast("frameEncoder",
                new ProtobufVarint32LengthFieldPrepender());
        //配置Protobuf编码器，发送的消息会先经过编码
        pipeline.addLast("protobufEncoder", new ProtobufEncoder());

    }*/
}
