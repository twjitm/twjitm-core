package com.twjitm.core.common.netstack.pipeline;

import com.twjitm.core.common.enums.MessageAttributeEnum;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.netstack.builder.NettyTcpSessionBuilder;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.tcp.AbstractNettyNetProtoBufTcpMessage;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import com.twjitm.core.common.service.INettyChannleOperationService;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author twjitm - [Created on 2018-08-02 20:41]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyTcpServerPipeLineImpl implements INettyServerPipeLine {

private Logger logger=LoggerUtils.getLogger(NettyTcpServerPipeLineImpl.class);
    @Override
    public void dispatch(Channel channel, AbstractNettyNetMessage message) {
        int commId = message.getNettyNetMessageHead().getCmd();
        MessageRegistryFactory messageRegistryFactory = SpringServiceManager.springLoadService.getMessageRegistryFactory();
        MessageComm messageComm = messageRegistryFactory.getMessageComm(commId);
        if (message instanceof AbstractNettyNetProtoBufTcpMessage) {
            AbstractNettyNetProtoBufTcpMessage protoBufTcpMessage = (AbstractNettyNetProtoBufTcpMessage) message;
            INettyChannleOperationService netTcpSessionLoopUpService = SpringServiceManager.springLoadService.getNetTcpSessionLoopUpService();
            long sessionId = channel.attr(NettyTcpSessionBuilder.SESSION_ID).get();
            NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.findNettySession(sessionId);
            if(nettySession==null){
                logger.error("NETTY SESSION IS NULL");
            }
            message.setAttribute(MessageAttributeEnum.DISPATCH_SESSION,nettySession);
            nettySession.addNettyNetMessage(message);
        }

    }
}
