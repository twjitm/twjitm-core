package com.twjitm.http;

import com.twjitm.core.common.entity.online.OnlineHeartClientHttpMessage;
import com.twjitm.core.common.netstack.coder.decode.http.NettyNetProtoBuffHttpToMessageDecoderFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

/**
 * Created by twjitm on 2017/9/29.
 */

public class GameHttpClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            System.err.println("STATUS: " + response.status());
            System.err.println("VERSION: " + response.protocolVersion());
            System.err.println();

            if (!response.headers().isEmpty()) {
                for (CharSequence name : response.headers().names()) {
                    for (CharSequence value : response.headers().getAll(name)) {
                        System.err.println("HEADER: " + name + " = " + value);
                    }
                }
                System.err.println();
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                System.err.println("CHUNKED CONTENT {");
            } else {
                System.err.println("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

            System.err.print(content.content().toString(CharsetUtil.UTF_8));
            System.err.flush();

            ByteBuf byteBuf = content.content();

            AbstractNettyNetProtoBufMessage netProtoBufMessage = null;
            //开始解析
            NettyNetProtoBuffHttpToMessageDecoderFactory netProtoBufHttpMessageDecoderFactory = new NettyNetProtoBuffHttpToMessageDecoderFactory();
            try {
                netProtoBufMessage = netProtoBufHttpMessageDecoderFactory.parseMessage(byteBuf);
            } catch (CodecException e) {
                e.printStackTrace();
            }

            if (netProtoBufMessage instanceof OnlineHeartClientHttpMessage) {
                OnlineHeartClientHttpMessage onlineHeartClientHttpMessage = (OnlineHeartClientHttpMessage) netProtoBufMessage;
                System.out.println("http 協議可以通訊了");
                System.out.println(onlineHeartClientHttpMessage.getPlayerId());
                System.out.println(onlineHeartClientHttpMessage.getPlayerName());
            }

            if (content instanceof LastHttpContent) {
                System.err.println("} END OF CONTENT");
                ctx.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
