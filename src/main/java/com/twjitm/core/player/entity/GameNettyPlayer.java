package com.twjitm.core.player.entity;

import com.twjitm.core.common.netstack.sender.NetTcpMessageSender;
import com.twjitm.core.common.service.ILongId;

/**
 * @author EGLS0807 - [Created on 2018-08-08 13:52]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class GameNettyPlayer implements IGameNettyPlayer, ILongId {

    //玩家id
    private long playerId;
    //玩家的udp token
    private int udpToken;

    private NetTcpMessageSender netTcpMessageSender;

    public GameNettyPlayer(long playerId, int udpToken, NetTcpMessageSender netTcpMessageSender) {
        this.playerId = playerId;
        this.udpToken = udpToken;
        this.netTcpMessageSender = netTcpMessageSender;
    }

    @Override
    public long getLongId() {
        return this.playerId;
    }

    @Override
    public long getPlayerId() {
        return this.playerId;
    }

    @Override
    public int getPlayerUdpToken() {
        return this.udpToken;
    }

    @Override
    public NetTcpMessageSender getNetTcpMessageSender() {
        return this.netTcpMessageSender;
    }

    public void setNetTcpMessageSender(NetTcpMessageSender netTcpMessageSender) {
        this.netTcpMessageSender = netTcpMessageSender;
    }
}