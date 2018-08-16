package com.twjitm.core.common.netstack.entity.http;


import com.twjitm.core.common.netstack.entity.NettyNetMessageHead;

/**
 * Created by 文江 on 2017/11/16.
 * http协议头
 */
public class NettyNetHttpMessageHead extends NettyNetMessageHead {
    private long playerId;
    private String token = "";

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
