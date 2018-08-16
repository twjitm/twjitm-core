package com.twjitm.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.ssl.SslContext;

/**
 * Created by twjitm on 2017/9/29.
 */
public class GameHttpClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public GameHttpClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast("encoder", new HttpRequestEncoder());
//        p.addLast("trunk", new HttpObjectAggregator(1048576));
        p.addLast("decoder", new HttpResponseDecoder());

        p.addLast(new GameHttpClientHandler());
    }
}

