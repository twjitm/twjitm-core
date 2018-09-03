package com.twjitm.core.test;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.spring.SpringLoadServiceImpl;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 文江 on 2018/5/16.
 */
public class TestServiceHandler extends ChannelInboundHandlerAdapter {
    Logger logger = LoggerUtils.getLogger(TestServiceHandler.class);
    private Map<String, Channel> map = new HashMap<String, Channel>();
    private List<Channel> list = new ArrayList<Channel>();
    private SpringLoadServiceImpl springLoadManager = SpringServiceManager.springLoadService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("有新客户端请求建立");
        /*  ctx.writeAndFlush("你好，客户端");*/
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        map.put(ctx.channel().id().asLongText(), ctx.channel());
        System.out.println("hahahahah");
        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
        System.out.println(ctx.channel().id());
        AbstractNettyNetProtoBufMessage message = springLoadManager.getDispatcherService().dispatcher((AbstractNettyNetProtoBufMessage) msg);
       /* ctx.channel().writeAndFlush("服务器对大家说的话！");
        for (Channel channel : list) {
            channel.writeAndFlush("广播的消息");
        }*/
        /*list.add(ctx.channel());
        ctx.flush();*/
        ctx.writeAndFlush(message);
    }

    public void test() {

    }
}
