package com.twjitm.core.common.process.tcp;

/**
 * @author twjitm - [Created on 2018-07-24 15:46]
 * @jdk java version "1.8.0_77"
 */

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;

/**
 *
 */
public interface INettyTcpNetProtoMessageProcess {
    public void processNetMessage();
    public void addNetMessage(AbstractNettyNetMessage abstractNettyNetMessage);
    public void close();
}
