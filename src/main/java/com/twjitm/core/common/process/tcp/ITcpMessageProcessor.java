package com.twjitm.core.common.process.tcp;

import com.twjitm.core.common.process.IMessageProcessor;

/**
 * @author twjitm - [Created on 2018-08-06 20:44]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public interface ITcpMessageProcessor extends IMessageProcessor {
    public void putDirectTcpMessage();
}
