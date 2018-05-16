package com.twjitm.core.common.dispatcher;


import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;

public interface IDispatcher {
    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetProtoBufMessage message);
}
