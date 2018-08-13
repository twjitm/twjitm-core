package com.twjitm.core.common.entity.online;

import com.twjitm.core.common.annotation.MessageCommandAnntation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.tcp.AbstractNettyNetProtoBufTcpMessage;
import com.twjitm.core.common.proto.LoginOnlineClientTcpMessagebuf;
import io.netty.handler.codec.CodecException;

/**
 * @author EGLS0807 - [Created on 2018-08-08 17:41]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
@MessageCommandAnntation(messagecmd = MessageComm.PLAYER_LOGIN_MESSAGE)
public class LoginOnlineClientTcpMessage extends AbstractNettyNetProtoBufTcpMessage {
    private long playerId;


    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        LoginOnlineClientTcpMessagebuf.LoginOnlineClientTcpMessage.Builder builder = LoginOnlineClientTcpMessagebuf.LoginOnlineClientTcpMessage.newBuilder();
        builder.setPlayerId(playerId);
        byte[] bytes=builder.build().toByteArray();
        this.getNetMessageBody().setBytes(bytes);
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        LoginOnlineClientTcpMessagebuf.LoginOnlineClientTcpMessage req = LoginOnlineClientTcpMessagebuf.LoginOnlineClientTcpMessage.parseFrom(bytes);
        playerId = req.getPlayerId();
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
