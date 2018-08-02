package com.twjitm.core.common.netstack.pipeline;

import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.netstack.builder.NettyTcpSessionBuilder;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.tcp.AbstractNettyNetProtoBufTcpMessage;
import com.twjitm.core.common.netstack.session.ISession;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import com.twjitm.core.common.service.INettyChannleOperationService;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.log4j.Logger;

/**
 * @author EGLS0807 - [Created on 2018-08-02 20:41]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class NettyTcpServerPipeLineImpl implements INettyServerPipeLine {

private Logger logger=LoggerUtils.getLogger(NettyTcpServerPipeLineImpl.class);
    @Override
    public void dispatch(Channel channel, AbstractNettyNetMessage message) {
        int commid = message.getNettyNetMessageHead().getCmd();
        MessageRegistryFactory messageRegistryFactory = SpringServiceManager.springLoadService.getMessageRegistryFactory();
        MessageComm messageComm = messageRegistryFactory.getMessageComm(commid);
        if (message instanceof AbstractNettyNetProtoBufTcpMessage) {
            AbstractNettyNetProtoBufTcpMessage protoBufTcpMessage = (AbstractNettyNetProtoBufTcpMessage) message;
            INettyChannleOperationService netTcpSessionLoopUpService = SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService();
            long sessionId = channel.attr(NettyTcpSessionBuilder.sessionId).get();
            NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.findNettySession(sessionId);
            if(nettySession==null){
                logger.error("netty session is null");
            }

            IDispatcherService dispatch = SpringServiceManager.springLoadService.getDispatcherService();
            dispatch.dispatcher(protoBufTcpMessage);
        }

    }
}
