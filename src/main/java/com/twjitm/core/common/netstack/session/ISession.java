package com.twjitm.core.common.netstack.session;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;

/**
 * @author twjitm - [Created on 2018-07-24 15:55]
 * @jdk java version "1.8.0_77"\
 * session��ҵ���߼�
 */
public interface ISession {
    /**
     * �жϵ�ǰ�Ự�Ƿ�������״̬
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
     * �����쳣ʱ�Ƿ�ر�����
     *
     * @return
     */
    public boolean closeOnException();

    public void write(byte[] msg) throws Exception;

}
