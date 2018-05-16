package com.twjitm.core.common.entity.online;

import io.netty.channel.Channel;

/**
 * Created by 文江 on 2017/10/28.
 */
public class OnlineUserPo  {
    private Channel channel;
    private String sessionId;
    private long uId;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }
}
