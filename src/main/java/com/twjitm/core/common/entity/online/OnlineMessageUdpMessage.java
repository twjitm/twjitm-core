package com.twjitm.core.common.entity.online;

import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.udp.AbstractNettyNetProtoBufUdpMessage;
import com.twjitm.core.common.proto.OnlineClientUdpMessageBuf;
import io.netty.handler.codec.CodecException;

/**
 * @author twjitm - [Created on 2018-08-14 14:31]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
@MessageCommandAnnotation(messageCmd = MessageComm.UDP_ONLINE_HEART_MESSAGE)
public class OnlineMessageUdpMessage extends AbstractNettyNetProtoBufUdpMessage {
    private long id;

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        OnlineClientUdpMessageBuf.OnlineClientUdpMessage.Builder builder = OnlineClientUdpMessageBuf.OnlineClientUdpMessage.newBuilder();
        this.id = builder.getId();
        builder.setId(this.getId());
        byte[] bytes = builder.build().toByteArray();
        getNetMessageBody().setBytes(bytes);

    }

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        OnlineClientUdpMessageBuf.OnlineClientUdpMessage message = OnlineClientUdpMessageBuf.OnlineClientUdpMessage.parseFrom(bytes);
        setId(message.getId());

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
