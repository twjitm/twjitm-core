package com.twjitm.core.common.logic.online.Impl;

import com.twjitm.core.common.entity.online.LoginOnlineClientTcpMessage;
import com.twjitm.core.common.enums.MessageAttributeEnum;
import com.twjitm.core.common.logic.online.LoginOnLineHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import com.twjitm.core.common.service.Impl.NettyGamePlayerFindServiceImpl;
import com.twjitm.core.player.entity.GameNettyPlayer;
import com.twjitm.core.spring.SpringLoadServiceImpl;
import com.twjitm.core.spring.SpringServiceManager;

/**
 * @author EGLS0807 - [Created on 2018-08-08 17:38]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class LoginOnLineHandlerImpl extends LoginOnLineHandler {
    @Override
    public AbstractNettyNetMessage loginImpl(LoginOnlineClientTcpMessage message) {
        //登录游戏简单

        long id = SpringServiceManager.springLoadService.getLongIdGenerator().generateId();
        long playerId = 520 + id;
        int token = 10086;
        NettyTcpSession session = (NettyTcpSession) message.getAttribute(MessageAttributeEnum.DISPATCH_SESSION);
        session.setPlayerId(playerId);
        GameNettyPlayer gameNettyPlayer=new GameNettyPlayer(playerId,token,session.getNetTcpMessageSender());
        NettyGamePlayerFindServiceImpl nettyGamePlayerLoopUpService = SpringServiceManager.springLoadService.getNettyGamePlayerLoopUpService();
        nettyGamePlayerLoopUpService.addT(gameNettyPlayer);
        return message;
    }
}
