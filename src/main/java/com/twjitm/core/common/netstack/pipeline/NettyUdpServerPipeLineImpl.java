package com.twjitm.core.common.netstack.pipeline;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.enums.MessageAttributeEnum;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.udp.AbstractNettyNetProtoBufUdpMessage;
import com.twjitm.core.common.netstack.session.udp.NettyUdpSession;
import com.twjitm.core.common.process.udp.NettyUdpNetProtoMessageProcessor;
import com.twjitm.core.common.service.INettyChannleOperationService;
import com.twjitm.core.common.service.Impl.NettyGamePlayerFindServiceImpl;
import com.twjitm.core.player.entity.GameNettyPlayer;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author EGLS0807 - [Created on 2018-08-02 20:42]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyUdpServerPipeLineImpl implements INettyServerPipeLine {
    Logger logger = LoggerUtils.getLogger(NettyUdpServerPipeLineImpl.class);

    @Override
    public void dispatch(Channel channel, AbstractNettyNetMessage message) {
        short messageCmdId = message.getNettyNetMessageHead().getCmd();
        MessageRegistryFactory messageRegistryFactory = SpringServiceManager.springLoadService.getMessageRegistryFactory();
        MessageComm messageComm = messageRegistryFactory.getMessageComm(messageCmdId);
        AbstractNettyNetProtoBufUdpMessage udpMessage = (AbstractNettyNetProtoBufUdpMessage) message;
        if (logger.isTraceEnabled()) {
            logger.info("DISPATCH　UDP MESSAGE ID" + messageComm.commId + "CLASS IS :" + udpMessage.getClass().getSimpleName());
        }
        //rpc暂时不出了

        long playerId = udpMessage.getPlayerId();
        NettyGamePlayerFindServiceImpl gamePlayerFindService = SpringServiceManager.springLoadService.getNettyGamePlayerLoopUpService();
        GameNettyPlayer gameNettyPlayer = gamePlayerFindService.findT(playerId);

        if (gameNettyPlayer == null) {
            if (logger.isTraceEnabled()) {
                logger.info("PLAYER ID　IS " + playerId + " NULL");
            }
            return;
        }
        message.setAttribute(MessageAttributeEnum.DISPATCH_SESSION, new NettyUdpSession(channel));
        //处理方式
        if (GlobalConstants.UDPServiceConfig.IS_UDP_MESSAGE_ORDER_QUEUE_FLAG) {
            //有序队列

        } else {
            //消费者模式
            NettyUdpNetProtoMessageProcessor udpNetProtoMessageProcessor = SpringServiceManager.springLoadService.getNettyUdpNetProtoMessageProcessor();
            udpNetProtoMessageProcessor.put(message);
        }

    }
}
