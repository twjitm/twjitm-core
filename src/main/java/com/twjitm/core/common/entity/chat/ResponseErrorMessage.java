package com.twjitm.core.common.entity.chat;

import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import io.netty.handler.codec.CodecException;

/**
 * Created by 文江 on 2017/11/8.
 */

/**
 * 异常返回消息统一对象
 */
@MessageCommandAnnotation(messageCmd = MessageComm.MESSAGE_TRUE_RETURN)
public class ResponseErrorMessage extends AbstractNettyNetProtoBufMessage {

    private short errorCode;
    private byte errType;
    private String message;

    public ResponseErrorMessage() {
        super();
    }

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {

    }

    public short getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(short errorCode) {
        this.errorCode = errorCode;
    }

    public byte getErrType() {
        return errType;
    }

    public void setErrType(byte errType) {
        this.errType = errType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
