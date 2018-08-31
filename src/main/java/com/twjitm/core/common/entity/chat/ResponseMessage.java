package com.twjitm.core.common.entity.chat;

import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.handler.codec.CodecException;

/**
 * Created by 文江 on 2017/11/8.
 */

/**
 * 正常返回消息对象
 */
@MessageCommandAnnotation(messageCmd = MessageComm.MESSAGE_TRUE_RETURN)
public class ResponseMessage extends AbstractNettyNetProtoBufMessage {



    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {

    }

    public void decoderNetJsonMessageBody(String json) {

    }

    public void encodeNetJsonMessageBody(String json) {

    }
}
