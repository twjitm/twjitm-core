package com.twjitm.core.test;

import com.twjitm.core.spring.SpringLoadManager;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 文江 on 2018/5/16.
 */
public class TestServiceHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Channel> map = new HashMap<String, Channel>();
    private List<Channel> list=new ArrayList<Channel>();
    private SpringLoadManager springLoadManager = SpringServiceManager.springLoadManager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        map.put(ctx.channel().id().asLongText(), ctx.channel());
        System.out.println("hahahahah");
        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
        System.out.println(ctx.channel().id());
        springLoadManager.getTestService().say();
        ctx.channel().writeAndFlush("服务器对大家说的话！");
        for (Channel channel:list){
            channel.writeAndFlush("广播的消息");
        }
        list.add(ctx.channel());
        ctx.flush();

    }
}
