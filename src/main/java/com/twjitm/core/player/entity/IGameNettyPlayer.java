package com.twjitm.core.player.entity;

import com.twjitm.core.common.netstack.sender.NetTcpMessageSender;

/**
 * @author EGLS0807 - [Created on 2018-08-08 13:55]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public interface IGameNettyPlayer {
    public long getPlayerId();
    public int getPlayerUdpToken();
    public NetTcpMessageSender getNetTcpMessageSender();

}
