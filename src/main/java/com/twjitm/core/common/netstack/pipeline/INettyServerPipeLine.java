package com.twjitm.core.common.netstack.pipeline;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import io.netty.channel.Channel;

/**
 * @author EGLS0807 - [Created on 2018-08-02 20:39]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public interface INettyServerPipeLine {
     void dispatch(Channel channel, AbstractNettyNetMessage message);

}
