package com.twjitm.core.common.entity.chat;


import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.handler.codec.CodecException;

import java.util.List;

@MessageCommandAnnotation(messageCmd = MessageComm.PUBLIC_CHART_MESSAGE)
public class BoradCastAllChatMessage extends AbstractNettyNetProtoBufMessage {
    private List<ChatMessage> messages;



    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }



    public void release() throws CodecException {

    }

    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {

    }

    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {

    }

    public void decoderNetJsonMessageBody(String json) {

    }

    public void encodeNetJsonMessageBody(String json) {

    }
}
