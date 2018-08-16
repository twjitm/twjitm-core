package com.twjitm.core.initalizer;

import com.twjitm.core.common.handler.http.NettyNetMessageHttpServerHandler;
import com.twjitm.core.common.service.http.AsyncNettyHttpHandlerService;
import com.twjitm.core.spring.SpringServiceManager;
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
        //use netty self encoder and decoder
        channelPipLine.addLast("encoder", new HttpResponseEncoder());
        channelPipLine.addLast("decoder", new HttpRequestDecoder());
        AsyncNettyHttpHandlerService asyncNettyHttpHandlerService = SpringServiceManager.getSpringLoadService().getAsyncNettyHttpHandlerService();
        channelPipLine.addLast(asyncNettyHttpHandlerService.getDefaultEventExecutorGroup(),new NettyNetMessageHttpServerHandler());

    }
}
