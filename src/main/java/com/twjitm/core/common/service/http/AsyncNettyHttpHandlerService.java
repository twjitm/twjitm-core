package com.twjitm.core.common.service.http;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.service.IService;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.stereotype.Service;

/**
 * @author EGLS0807 - [Created on 2018-08-16 12:17]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
@Service
public class AsyncNettyHttpHandlerService implements IService {
    private DefaultEventExecutorGroup defaultEventExecutorGroup;


    @Override
    public String getId() {
        return DefaultEventExecutorGroup.class.getSimpleName();
    }

    @Override
    public void startup() throws Exception {
        defaultEventExecutorGroup = new DefaultEventExecutorGroup(GlobalConstants.NettyNet.NETTY_NET_HTTP_MESSAGE_THREAD_CORE_NUMBER);
    }

    @Override
    public void shutdown() throws Exception {
        if(defaultEventExecutorGroup != null){
            defaultEventExecutorGroup.shutdownGracefully();
        }
    }

    public DefaultEventExecutorGroup getDefaultEventExecutorGroup() {
        return defaultEventExecutorGroup;
    }
}
