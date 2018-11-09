package com.twjitm.core.common.netstack.pipeline;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import io.netty.channel.Channel;

/**
 * 管道消息分发器。 管道消息分发器，主要是讲tcp消息或者rpc消息，udp消息以及http消息
 * 进行分发，由实现类自己完成实现，
 *
 * @author twjitm - [Created on 2018-08-02 20:39]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public interface INettyServerPipeLine {
    /**
     * 分发器
     *
     * @param channel
     * @param message
     */
    void dispatch(Channel channel, AbstractNettyNetMessage message);

}
