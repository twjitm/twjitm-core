package com.twjitm;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.common.netstack.entity.NettyNetMessageHead;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by 文江 on 2017/11/13.
 */
public class TestClientHandler extends ChannelInboundHandlerAdapter {
private AbstractNettyNetProtoBufMessage message;

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
        NettyNetMessageHead head=new NettyNetMessageHead();
        NettyNetMessageBody body=new NettyNetMessageBody();
        head.setCmd((short) 5);
        head.setHead((short) 3);
        body.setBytes("hello".getBytes((CharsetUtil.UTF_8)));
        System.out.println("into handleradded");
        String context = "helloworld";
       /* BaseMessageProto.BaseMessageProBuf.Builder builder = BaseMessageProto.BaseMessageProBuf.newBuilder();
        builder.setCommid(MessageComm.PRIVATE_CHAT_MESSAGE.commId);
        builder.setLength(context.length());
        builder.setUId(1l);
        builder.setSessionId(111l);
        builder.setTimeStamp(System.currentTimeMillis());
        BaseMessageProto.ChatMessageProBuf.Builder chatMessageProBuf = BaseMessageProto.ChatMessageProBuf.newBuilder();
        ///builder.mergeFrom(chatMessageProBuf);
        chatMessageProBuf.setChatType(0);
        chatMessageProBuf.setContext("hello");
        chatMessageProBuf.setRead(false);
        chatMessageProBuf.setReceiveHaldUrl("http://img");
        chatMessageProBuf.setReceiveNickName("twjitm");
        chatMessageProBuf.setReceiveSession("ggg");
        chatMessageProBuf.setReceiveUId(1);
        chatMessageProBuf.setBaseMessageBuf(builder);*/
        ctx.writeAndFlush(context);
    }
}
