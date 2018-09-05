package com.twjitm.core.common.netstack.entity.udp;


import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;

import java.net.InetSocketAddress;

/**
 * Created by twjtim on 2017/2/16.
 * 抽象的udp消息
 */
public abstract class AbstractNettyNetProtoBufUdpMessage extends AbstractNettyNetProtoBufMessage {
    /**
     * 发送方
     */
    private InetSocketAddress send;
    /**
     * 接收方
     */
    private InetSocketAddress receive;


    public InetSocketAddress getSend() {
        return send;
    }

    public void setSend(InetSocketAddress send) {
        this.send = send;
    }

    public InetSocketAddress getReceive() {
        return receive;
    }

    public void setReceive(InetSocketAddress receive) {
        this.receive = receive;
    }

    public AbstractNettyNetProtoBufUdpMessage(){
        super();
        setNettyNetMessageHead(new NettyUDPMessageHead());
        setNettyNetMessageBody(new NettyNetMessageBody());
        initHeadCommId();
    }

    public void setPlayerId(long playerId) {
        NettyUDPMessageHead netUdpMessageHead = (NettyUDPMessageHead) getNetMessageHead();
        netUdpMessageHead.setPlayerId(playerId);
    }

    public void setTocken(int tocken){
        NettyUDPMessageHead netUdpMessageHead = (NettyUDPMessageHead) getNetMessageHead();
        netUdpMessageHead.setTocken(tocken);
    }

    public long getPlayerId(){
        NettyUDPMessageHead netUdpMessageHead = (NettyUDPMessageHead) getNetMessageHead();
        return netUdpMessageHead.getPlayerId();
    }

    public int getTocken(){
        NettyUDPMessageHead netUdpMessageHead = (NettyUDPMessageHead) getNetMessageHead();
        return netUdpMessageHead.getTocken();
    }
}
