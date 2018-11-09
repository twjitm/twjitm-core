package com.twjitm.core.common.logic.online;

import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.entity.online.LoginOnlineClientTcpMessage;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.logic.handler.AbstractBaseHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

/**
 * @author twjitm - [Created on 2018-08-08 17:35]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public abstract class LoginOnLineHandler extends AbstractBaseHandler {
    Logger logger = LoggerUtils.getLogger(LoginOnLineHandler.class);

    @MessageCommandAnnotation(messageCmd = MessageComm.PLAYER_LOGIN_MESSAGE)
    public AbstractNettyNetMessage login(LoginOnlineClientTcpMessage message) {
        logger.info("INVOKE HANDLER BEGAN.......loginImpl()");
        return loginImpl(message);
    }

    public abstract AbstractNettyNetMessage loginImpl(LoginOnlineClientTcpMessage message);
}
