package com.twjitm.core.common.factory;

import com.twjitm.core.common.entity.chat.ResponseErrorMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import org.springframework.stereotype.Service;

/**
 * @author twjitm - [Created on 2018-07-26 21:04]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyTcpMessageFactory implements INettyTcpMessageFactory {
    @Override
    public AbstractNettyNetMessage createCommonErrorResponseMessage(int serial, int state, String tip) {
        ResponseErrorMessage message = new ResponseErrorMessage();
        message.setSerial(serial);
        message.setErrorCode((short) 500);
        message.setMessage(tip);
        return message;
    }

    @Override
    public AbstractNettyNetMessage createCommonErrorResponseMessage(int serial, int state) {
        ResponseErrorMessage message = new ResponseErrorMessage();
        message.setSerial(serial);
        message.setErrorCode((short) 500);
        message.setMessage("default");
        return message;
    }

    @Override
    public AbstractNettyNetMessage createCommonResponseMessage(int serial) {
        ResponseErrorMessage message = new ResponseErrorMessage();
        message.setSerial(serial);
        message.setErrorCode((short) 200);
        message.setMessage("successful");
        return message;
    }
}
