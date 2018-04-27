package com.twjitm.core.netstack.entity.tcp;


import com.twjitm.core.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.netstack.entity.NettyNetMessageHead;
import com.twjitm.core.netstack.entity.NettyNetProtoBufMessageBody;

/**
 * Created by 文江 on 2017/11/15.
 */
public abstract class AbstractNettyNetProtoBufTcpMessage extends AbstractNettyNetProtoBufMessage {
    public AbstractNettyNetProtoBufTcpMessage(String json) {
        super(json);
        setNettyNetMessageHead(new NettyNetMessageHead());
        setNettyNetMessageBody(new NettyNetProtoBufMessageBody());
        initHeadCommId();
    }
}
