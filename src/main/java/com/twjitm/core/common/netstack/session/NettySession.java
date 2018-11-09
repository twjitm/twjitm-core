package com.twjitm.core.common.netstack.session;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import io.netty.channel.Channel;

/**
 * @author twjitm - [Created on 2018-07-24 15:56]
 * @jdk java version "1.8.0_77"
 * 自定义session
 */
public abstract class NettySession implements ISession {
    public volatile Channel channel;
    private long playerId;


    public NettySession(Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean isConnected() {
        if (channel != null) {
            return channel.isActive();
        }
        return false;
    }

    @Override
    public void write(AbstractNettyNetMessage msg) throws Exception {
        if (msg != null) {
            try {
                //防止客户端被重置
                if (channel.isActive()) {
                    channel.writeAndFlush(msg);
                }
            } catch (Exception e) {
                throw new Exception();
            }

        }
    }

    @Override
    public void close(boolean immediately) {
        if (channel != null) {
            channel.close();
        }
    }

    @Override
    public boolean closeOnException() {
        return false;
    }

    @Override
    public void write(byte[] msg) throws Exception {
        if (channel != null) {
            try {
                channel.writeAndFlush(msg);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
