package com.twjitm.core.common.factory;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import org.springframework.stereotype.Service;

/**
 * @author EGLS0807 - [Created on 2018-07-26 21:04]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyTcpMessageFactory implements  INettyTcpMessageFactory{
    @Override
    public AbstractNettyNetMessage createCommonErrorResponseMessage(int serial, int state, String tip) {
        return null;
    }

    @Override
    public AbstractNettyNetMessage createCommonErrorResponseMessage(int serial, int state) {
        return null;
    }

    @Override
    public AbstractNettyNetMessage createCommonResponseMessage(int serial) {
        return null;
    }
}
