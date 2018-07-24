package com.twjitm.core.common.process;

import com.google.protobuf.AbstractMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

/**
 * @author twjitm - [Created on 2018-07-24 20:19]
 * @jdk java version "1.8.0_77"
 * <p>
 * 消息处理
 */
public class NettyNetMessageProcessLogic {
      Logger logger = LoggerUtils.getLogger(this.getClass());

    /**
     * 处理tcp消息
     *
     * @param message
     * @param session
     */
    public  void processTcpMessage(AbstractNettyNetMessage message, NettySession session) {
        long begin = System.nanoTime();
        IDispatcherService dispatcherService = SpringServiceManager.springLoadManager.getDispatcherService();
        AbstractNettyNetProtoBufMessage respone = dispatcherService.dispatcher(message);
        if (respone != null) {
            respone.getNetMessageHead().setSerial(message.getNettyNetMessageHead().getSerial());
            try {
                session.write(respone);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }
        }
        long end = System.nanoTime();

    }


}
