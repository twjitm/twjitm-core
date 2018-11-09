package com.twjitm.core.common.entity.online;

import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.http.AbstractNettyNetProtoBufHttpMessage;
import com.twjitm.core.common.proto.OnlineHeratClientHttpMessageBuf;
import io.netty.handler.codec.CodecException;

/**
 * @author twjitm - [Created on 2018-08-16 16:00]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
@MessageCommandAnnotation(messageCmd = MessageComm.HTTP_ONLINE_HEART_MESSAGE)
public class OnlineHeartClientHttpMessage extends AbstractNettyNetProtoBufHttpMessage {
    private long playerId;
    private String playerName;

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        OnlineHeratClientHttpMessageBuf.OnlineClientHttpMessage.Builder builder = OnlineHeratClientHttpMessageBuf.OnlineClientHttpMessage.newBuilder();
        builder.setPlayerId(this.getPlayerId());
        builder.setPlayerName(this.getPlayerName());
        byte[] bytes = builder.build().toByteArray();
        getNetMessageBody().setBytes(bytes);
    }

    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {
        byte[] bytes = getNetMessageBody().getBytes();
        OnlineHeratClientHttpMessageBuf.OnlineClientHttpMessage message = OnlineHeratClientHttpMessageBuf.OnlineClientHttpMessage.parseFrom(bytes);
        this.playerId = message.getPlayerId();
        this.playerName = message.getPlayerName();
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }
}
