package com.twjitm.core.common.handler.socket;

import com.alibaba.fastjson.JSON;
import com.twjitm.core.common.entity.online.OnlineUserBroadCastMessage;
import com.twjitm.core.common.entity.online.OnlineUserPo;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by 文江 on 2017/9/25.
 * 玩家进入房间处理
 */
public class NettyCommonSessionWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static Logger logger = LogManager.getLogger(NettyCommonSessionWebSocketHandler.class.getName());
    public volatile static Map<Long, OnlineUserPo> onlineUserMap = new ConcurrentHashMap<Long, OnlineUserPo>();

    /**
     * 消息到来
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        ByteBuf buff = msg.copy().content();
        Channel incoming = ctx.channel();
        String json = msg.text();
    }

    private void sendMessagetoClient(Channel channel, AbstractNettyNetProtoBufMessage message) {
        String json = JSON.toJSONString(message);
        channel.writeAndFlush(new TextWebSocketFrame(json));
    }

    /**
     * 覆盖了 handlerAdded() 事件处理方法。每当从服务端收到新的客户端连接时，客户端的 Channel
     * 存入 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        OnlineUserBroadCastMessage broadCastMessagePo = new OnlineUserBroadCastMessage();
       // broadCastMessagePo.setCommId(MessageComm.getVaule(MessageComm.PLAYER_LOGIN_MESSAGE));
        broadCastMessagePo.setOutOrInType(0);
        broadCastMessagePo.setMessageTime(new Date().getTime());
        // broadCastMessagePo.setMessageType(MessageType.PLAYER_LOGIN_MESSAGE);
        // dispatcherNetty(incoming, JSON.toJSONString(broadCastMessagePo));
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
        }
        // if (onlineUserMap.containsKey())
        channels.add(ctx.channel());
        logger.info("Client:" + incoming.remoteAddress() + "加入");
    }

    /**
     * 覆盖了 handlerRemoved() 事件处理方法。每当从服务端收到客户端断开时，
     * 客户端的 Channel 移除 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
        }
        logger.info("Client:" + incoming.remoteAddress() + "离开");
        channels.remove(ctx.channel());
    }

    /**
     * 覆盖了 channelRead0() 事件处理方法。每当从服务端读到客户端写入信息时，将信息转发给其他客户端的 Channel。
     * 其中如果你使用的是 Netty 5.x 版本时，需要把 channelRead0() 重命名为messageReceived()
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        logger.info("Client:" + incoming.remoteAddress() + "在线");
    }

    /**
     * .覆盖了 channelActive() 事件处理方法。服务端监听到客户端活动
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        logger.info("Client:" + incoming.remoteAddress() + "掉线");
    }

    /**
     * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty
     * 由于 IO 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，
     * 捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
     * 然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现
     * ，比如你可能想在关闭连接之前发送一个错误码的响应消息。
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        logger.info("Client:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
