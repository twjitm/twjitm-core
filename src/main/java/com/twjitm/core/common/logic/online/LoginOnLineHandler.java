package com.twjitm.core.common.logic.online;

import com.twjitm.core.common.annotation.MessageCommandAnntation;
import com.twjitm.core.common.entity.online.LoginOnlineClientTcpMessage;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.logic.handler.AbstractBaseHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;

/**
 * @author EGLS0807 - [Created on 2018-08-08 17:35]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public abstract class LoginOnLineHandler extends AbstractBaseHandler {


    @MessageCommandAnntation(messagecmd = MessageComm.PLAYER_LOGIN_MESSAGE)
    public AbstractNettyNetMessage login(LoginOnlineClientTcpMessage message){
        return loginImpl(message);
    }
    public abstract  AbstractNettyNetMessage loginImpl(LoginOnlineClientTcpMessage message);
}
