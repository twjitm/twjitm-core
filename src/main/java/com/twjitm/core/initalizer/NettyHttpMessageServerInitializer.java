package com.twjitm.core.initalizer;

import com.twjitm.core.common.handler.http.NettyNetMessageHttpServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyHttpMessageServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipLine = socketChannel.pipeline();
        //netty 自帶編解碼
        channelPipLine.addLast("encoder", new HttpResponseEncoder());
        channelPipLine.addLast("decoder", new HttpRequestDecoder());
        channelPipLine.addLast(new NettyNetMessageHttpServerHandler());

    }
}
