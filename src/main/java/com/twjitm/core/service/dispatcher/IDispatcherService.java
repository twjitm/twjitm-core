package com.twjitm.core.service.dispatcher;

import com.twjitm.core.netstack.entity.AbstractNettyNetProtoBufMessage;

/**
 * Created by ÎÄ½­ on 2018/4/27.
 */
public interface IDispatcherService {
    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetProtoBufMessage message);
    public String getMessage(String message);

}
