package com.twjitm.core.common.netstack.session;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;

/**
 * @author EGLS0807 - [Created on 2018-07-24 15:55]
 * @jdk java version "1.8.0_77"\
 * session的业务逻辑
 */
public interface ISession {
    /**
     * 判断当前会话是否处于连接状态
     *
     * @return
     */
    public boolean isConnected();

    /**
     * @param msg
     */
    public void write(AbstractNettyNetMessage msg) throws Exception;

    /**
     *
     */
    public void close(boolean immediately);

    /**
     * 出现异常时是否关闭连接
     *
     * @return
     */
    public boolean closeOnException();

    public void write(byte[] msg) throws Exception;

}
