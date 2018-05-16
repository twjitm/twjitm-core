package com.twjitm.core.common.netstack.entity.udp;


import com.twjitm.core.common.netstack.entity.NettyNetMessageHead;

/**
 * Created by 文江 on 2017/11/15.
 */
public class NettyUDPMessageHead extends NettyNetMessageHead {
    private long playerId;
    private int tocken;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getTocken() {
        return tocken;
    }

    public void setTocken(int tocken) {
        this.tocken = tocken;
    }
}
