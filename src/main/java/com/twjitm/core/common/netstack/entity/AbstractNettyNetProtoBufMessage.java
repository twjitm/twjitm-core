package com.twjitm.core.common.netstack.entity;

import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import io.netty.handler.codec.CodecException;


/**
 * Created by twjitm on 2017/11/15.
 * 基础protobuf协议消息
 */
public abstract class AbstractNettyNetProtoBufMessage extends AbstractNettyNetMessage {

    public AbstractNettyNetProtoBufMessage() {
        setNettyNetMessageHead(new NettyNetMessageHead());
        setNettyNetMessageBody(new NettyNetMessageBody());
    }

    @Override
    public NettyNetMessageHead getNetMessageHead() {
        return getNettyNetMessageHead();
    }

    @Override
    public NettyNetMessageBody getNetMessageBody() {
        return getNettyNetMessageBody();
    }

    protected void initHeadCommId() {
        MessageCommandAnnotation messageCommandAnnotation = this.getClass().getAnnotation(MessageCommandAnnotation.class);
        if (messageCommandAnnotation != null) {
            getNetMessageHead().setCmd((short) messageCommandAnnotation.messageCmd().commId);
        }
    }

    /**
     * 释放message的body
     */
    public void releaseMessageBody() throws CodecException, Exception {
        getNetMessageBody().setBytes(null);
    }

    public abstract void release() throws CodecException;

    public abstract void encodeNetProtoBufMessageBody() throws CodecException, Exception;

    public abstract void decoderNetProtoBufMessageBody() throws CodecException, Exception;

    public void setSerial(int serial) {
        getNetMessageHead().setSerial(serial);
    }


}
