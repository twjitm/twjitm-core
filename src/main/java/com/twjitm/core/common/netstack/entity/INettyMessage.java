package com.twjitm.core.common.netstack.entity;


import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.common.netstack.entity.NettyNetMessageHead;

/**
 * Created by 文江 on 2017/11/15.
 * 基础协议
 */
public interface INettyMessage {
    public NettyNetMessageHead getNetMessageHead();
    public NettyNetMessageBody getNetMessageBody();
}
