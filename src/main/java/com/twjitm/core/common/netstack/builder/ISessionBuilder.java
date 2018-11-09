package com.twjitm.core.common.netstack.builder;

import com.twjitm.core.common.netstack.session.ISession;
import io.netty.channel.Channel;

/**
 * @author twjitm - [Created on 2018-07-26 20:09]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public interface ISessionBuilder {

    /**
     * 绑定session，将netty的{@link Channel}
     * 组件和自定义{@link com.twjitm.core.common.netstack.session.NettySession}对象进行绑定
     *
     * @param channel
     * @return
     */
    public ISession buildSession(Channel channel);

}
