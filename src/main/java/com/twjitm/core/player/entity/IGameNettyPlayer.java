package com.twjitm.core.player.entity;

import com.twjitm.core.common.netstack.sender.NettyNetTcpMessageSender;

/**
 * @author twjitm - [Created on 2018-08-08 13:55]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public interface IGameNettyPlayer {
    public long getPlayerId();
    public int getPlayerUdpToken();
    public NettyNetTcpMessageSender getNetTcpMessageSender();

}
