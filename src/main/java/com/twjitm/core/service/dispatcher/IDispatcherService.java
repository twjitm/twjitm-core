package com.twjitm.core.service.dispatcher;


import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.rpc.NettyRpcRequestMessage;

/**
 * Created by  twjitm on 2018/4/27.
 */
public interface IDispatcherService {
    /**
     * 网络消息分发器
     * @param message
     * @return
     */
    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetMessage message);

    /**
     * rpc消息分发器
     * @param request
     * @return
     * @throws Throwable
     */
    public Object dispatcher(NettyRpcRequestMessage request) throws Throwable;

}
