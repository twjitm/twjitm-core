package com.twjitm.core.common.netstack.builder;

import com.twjitm.core.common.netstack.session.ISession;
import io.netty.channel.Channel;

/**
 * @author EGLS0807 - [Created on 2018-07-26 20:09]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public interface ISessionBuilder {

    public ISession buildSession(Channel channel);

}
