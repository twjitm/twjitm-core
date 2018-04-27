package com.twjitm.core.netstack;


import com.twjitm.core.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.netstack.entity.NettyNetMessageHead;

/**
 * Created by 文江 on 2017/11/15.
 * 基础协议
 */
public interface INettyMessage {
    public NettyNetMessageHead getNetMessageHead();
    public NettyNetMessageBody getNetMessageBody();
}
