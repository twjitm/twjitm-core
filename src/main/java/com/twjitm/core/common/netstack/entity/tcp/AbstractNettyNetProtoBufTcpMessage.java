package com.twjitm.core.common.netstack.entity.tcp;


import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.NettyNetMessageBody;
import com.twjitm.core.common.netstack.entity.NettyNetMessageHead;

/**
 * Created by 文江 on 2017/11/15.
 */
public abstract class AbstractNettyNetProtoBufTcpMessage extends AbstractNettyNetProtoBufMessage {
    public AbstractNettyNetProtoBufTcpMessage() {
        super();
        setNettyNetMessageHead(new NettyNetMessageHead());
        setNettyNetMessageBody(new NettyNetMessageBody());
        initHeadCommId();
    }
}
