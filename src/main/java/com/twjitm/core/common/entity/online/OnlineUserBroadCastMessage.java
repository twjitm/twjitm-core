package com.twjitm.core.common.entity.online;

import com.alibaba.fastjson.JSON;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.handler.codec.CodecException;

/**
 * 玩家广播上线／下现消息
 */
public class OnlineUserBroadCastMessage extends AbstractNettyNetProtoBufMessage {

    private int outOrInType;//类型
    private long messageTime;//时间

    public OnlineUserBroadCastMessage() {
    }

    public void release() throws CodecException {

    }

    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {

    }

    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {

    }





    public void decodeBody(Object in) {

    }

    public void encodeBody(Object out) {

    }

    /*public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    public int getOutOrInType() {
        return outOrInType;
    }

    public void setOutOrInType(int outOrInType) {
        this.outOrInType = outOrInType;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
