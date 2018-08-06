package com.twjitm.core.common.process.tcp;

import com.twjitm.core.common.process.IMessageProcessor;

/**
 * @author EGLS0807 - [Created on 2018-08-06 20:44]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public interface ITcpMessageProcessor extends IMessageProcessor {
    public void putDirectTcpMessage();
}
