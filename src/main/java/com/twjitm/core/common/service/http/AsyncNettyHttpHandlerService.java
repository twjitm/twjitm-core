package com.twjitm.core.common.service.http;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.service.IService;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.stereotype.Service;

/**
 *
 * 当业务代码中存在对EventLoop线程阻塞的时候，ChannelPipeline
 * 有一些能接收一个EventExecutorGroup的add()方法，如果一个事件
 * 被专递给自定义的一个EventExecutorGroup，他将会包含在这个EventExecutorGroup
 * 中的某个EventExecutor所处理，从而被Channel本身的EventLoop移除，对于这种用法
 * Netty提供了一个种叫做{@link io.netty.util.concurrent.DefaultEventExecutorGroup}的实现
 * 其实也是本类主要的一个核心API，
 * @author twjitm - [Created on 2018-08-16 12:17]
 * @company https://github.com/twjitm/
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
