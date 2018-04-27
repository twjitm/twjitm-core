package com.twjitm.core.netstack.entity;

import com.alibaba.fastjson.JSON;
import com.twjitm.core.netstack.INettyMessage;

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

    public AbstractNettyNetMessage(String json) {
        nettyNetMessageHead= JSON.parseObject(json,NettyNetMessageHead.class);
    }

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

    public ConcurrentHashMap<Object, Object> getAttributes() {
        return attributes;
    }
    public  Object getAttribute(Object key){
        return attributes.get(key);
    }
    public  void remove(Object key){
       attributes.remove(key);
    }

    /**
     * 初始化协议头
     * @param json
     */
    public  abstract void  initNettyNetMessageHead(String json);
    public  abstract  void decoderNetJsonMessageBody(String json);
    public  abstract  void encodeNetJsonMessageBody(String json);
    public  static   int getCmdToJson(String json){
     return 0;
    };
}
