package com.twjitm.core.service.dispatcher;


import com.google.protobuf.AbstractMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;

/**
 * Created by ÎÄ½­ on 2018/4/27.
 */
public interface IDispatcherService {
    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetMessage message);
    public String getMessage(String message);

}
