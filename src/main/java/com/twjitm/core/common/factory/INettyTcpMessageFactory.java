package com.twjitm.core.common.factory;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;

/**
 * @author twjitm - [Created on 2018-07-26 21:03]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public interface INettyTcpMessageFactory {
    /**
     * 创建一个错误消息
     *
     * @param serial
     * @param state
     * @param tip
     * @return
     */
    public AbstractNettyNetMessage createCommonErrorResponseMessage(int serial, int state, String tip);

    public AbstractNettyNetMessage createCommonErrorResponseMessage(int serial, int state);

    /**
     * 创建一个正常返回消息
     *
     * @param serial
     * @return
     */
    public AbstractNettyNetMessage createCommonResponseMessage(int serial);
}
