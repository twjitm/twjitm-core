package com.twjitm.core.common.netstack.entity;

import com.google.protobuf.AbstractMessage;

/**
 * Created by 文江 on 2017/11/15.
 */
public class NettyNetProtoBufMessageBody extends  NettyNetMessageBody{
    //将字节读取为protobuf的抽象对象
    private AbstractMessage abstractMessage;

    public AbstractMessage getAbstractMessage() {
        return abstractMessage;
    }

    public void setAbstractMessage(AbstractMessage abstractMessage) {
        this.abstractMessage = abstractMessage;
    }
}
