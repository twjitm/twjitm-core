package com.twjitm.core.common.netstack.entity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 文江 on 2017/11/15.
 */
public abstract class AbstractNettyNetMessage implements INettyMessage {
    public NettyNetMessageHead nettyNetMessageHead;
    public NettyNetMessageBody nettyNetMessageBody;
    /**
     * 增加默认属性(附带逻辑调用需要的属性)
     */
    private final ConcurrentHashMap<Object, Object> attributes = new ConcurrentHashMap<Object, Object>(3);


    public NettyNetMessageHead getNettyNetMessageHead() {
        return nettyNetMessageHead;
    }

    public void setNettyNetMessageHead(NettyNetMessageHead nettyNetMessageHead) {
        this.nettyNetMessageHead = nettyNetMessageHead;
    }

    public NettyNetMessageBody getNettyNetMessageBody() {
        return nettyNetMessageBody;
    }

    public void setNettyNetMessageBody(NettyNetMessageBody nettyNetMessageBody) {
        this.nettyNetMessageBody = nettyNetMessageBody;
    }

    public void setAttribute(Object key,Object value){
        attributes.put(key,value);
    }

    public  Object getAttribute(Object key){
        return attributes.get(key);
    }
    public  void remove(Object key){
       attributes.remove(key);
    }

    @Override
    public String toString() {
        return "AbstractNettyNetMessage{" +
                "nettyNetMessageHead=" + nettyNetMessageHead +
                ", nettyNetMessageBody=" + nettyNetMessageBody +
                ", attributes=" + attributes +
                '}';
    }
}
