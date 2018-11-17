package com.twjitm.core.common.netstack.sender;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;

/**
 * @author twjitm - [Created on 2018-07-24 16:23]
 *
 * @jdk java version "1.8.0_77"
 */
public interface INetMessageSender {

    /**
     *
     *
     * @param abstractNettyNetMessage
     * @return
     */
    public boolean sendMessage(AbstractNettyNetMessage abstractNettyNetMessage);

    /**
     *
     */
    public void close();
}
