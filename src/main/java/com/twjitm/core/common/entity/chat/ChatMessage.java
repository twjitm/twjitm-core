package com.twjitm.core.common.entity.chat;

import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.tcp.AbstractNettyNetProtoBufTcpMessage;
import com.twjitm.core.common.proto.BaseMessageProto;
import io.netty.handler.codec.CodecException;

/**
 * Created by 文江 on 2017/10/27.
 * 基于tcp或者是udp的聊天消息：聊天消息类型
 */
@MessageCommandAnnotation(messageCmd = MessageComm.PRIVATE_CHAT_MESSAGE)
public class ChatMessage extends AbstractNettyNetProtoBufTcpMessage {
    private int chatType;//文件，语言，文字，等
    private String context;//消息内容
    private long receiveUId;//接收者id
    private String receiveSession;//接收者session
    private String receiveNickName;//接收者昵称
    private String receiveHaldUrl;//接收者头像
    private boolean read;//接收者是否阅读

    @Override
    public void release() throws CodecException {

    }

    @Override
    public void encodeNetProtoBufMessageBody() throws CodecException, Exception {
        BaseMessageProto.ChatMessageProBuf.Builder builder = BaseMessageProto.ChatMessageProBuf.newBuilder();
        builder.setChatType(getChatType());
        builder.setContext(getContext());
        builder.setReceiveHaldUrl(getReceiveHaldUrl());
        builder.setRead(isRead());
        builder.setReceiveNickName(getReceiveNickName());
        builder.setReceiveSession(getReceiveSession());
        builder.setReceiveUId(getReceiveUId());
        byte[] bytes = builder.build().toByteArray();
        getNetMessageBody().setBytes(bytes);
    }

    /**
     * proto方式解码
     *
     * @throws CodecException
     * @throws Exception
     */
    @Override
    public void decoderNetProtoBufMessageBody() throws CodecException, Exception {
        byte[] bytes = getNettyNetMessageBody().getBytes();
        BaseMessageProto.ChatMessageProBuf req = BaseMessageProto.ChatMessageProBuf.parseFrom(bytes);
        setChatType(req.getChatType());
        setReceiveUId(req.getReceiveUId());
        setReceiveSession(req.getReceiveSession());
        setRead(req.getRead());
        setReceiveNickName(req.getReceiveNickName());
        setReceiveHaldUrl(req.getReceiveHaldUrl());
        setContext(getContext());
    }

    public ChatMessage() {

    }


    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public String getReceiveSession() {
        return receiveSession;
    }

    public void setReceiveSession(String receiveSession) {
        this.receiveSession = receiveSession;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


    public String getReceiveNickName() {
        return receiveNickName;
    }

    public void setReceiveNickName(String receiveNickName) {
        this.receiveNickName = receiveNickName;
    }

    public String getReceiveHaldUrl() {
        return receiveHaldUrl;
    }

    public void setReceiveHaldUrl(String receiveHaldUrl) {
        this.receiveHaldUrl = receiveHaldUrl;
    }

    public long getReceiveUId() {
        return receiveUId;
    }

    public void setReceiveUId(long receiveUId) {
        this.receiveUId = receiveUId;
    }


}
