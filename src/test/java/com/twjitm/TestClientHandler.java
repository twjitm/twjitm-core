package com.twjitm;

import com.twjitm.core.common.entity.online.LoginOnlineClientTcpMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 文江 on 2017/11/13.
 */
public class TestClientHandler extends ChannelInboundHandlerAdapter {
private Logger logger=LoggerFactory.getLogger(TestClientHandler.class);

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {


    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("-----------error--------------"+cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
     /* System.out.println("into channelActive");
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setChatType(1);
        chatMessage.setContext("twjitm");
        chatMessage.setReceiveHaldUrl("url");
        chatMessage.setRead(true);
        chatMessage.setReceiveNickName("haha");
        chatMessage.setReceiveSession("gaga");
        chatMessage.setReceiveUId(11);
        ctx.writeAndFlush(chatMessage);*/
     for(int i=0;i<100;i++){
         LoginOnlineClientTcpMessage login=new LoginOnlineClientTcpMessage();
         login.setPlayerId(Long.valueOf(10086));
         ctx.writeAndFlush(login);
         logger.info("send message to server................................");
     }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        System.out.println(msg.toString());
    }
}
