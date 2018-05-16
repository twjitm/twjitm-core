package com.twjitm.core.common.netstack.entity.udp;


import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;

/**
 * Created by 文江 on 2017/11/15.
 */
public class NettyUDpMessageBody extends NettyNetMessageBody {
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
