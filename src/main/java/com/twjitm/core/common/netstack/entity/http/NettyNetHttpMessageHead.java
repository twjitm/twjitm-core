package com.twjitm.core.common.netstack.entity.http;


import com.twjitm.core.common.netstack.entity.NettyNetMessageHead;

/**
 * Created by 文江 on 2017/11/16.
 * http协议头
 */
public class NettyNetHttpMessageHead extends NettyNetMessageHead {
    private long playerId;
    private String tocken="";

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getTocken() {
        return tocken;
    }

    public void setTocken(String tocken) {
        this.tocken = tocken;
    }
}
