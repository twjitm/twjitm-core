package com.twjitm.core.common.entity.chat;

import com.twjitm.core.common.annotation.MessageCommandAnntation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.handler.codec.CodecException;

/**
 * Created by 文江 on 2017/11/8.
 */

/**
 * 正常返回消息对象
 */
@MessageCommandAnntation(messagecmd = MessageComm.MESSAGE_TRUE_RETURN)
public class ResponseMessage extends AbstractNettyNetProtoBufMessage {



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
