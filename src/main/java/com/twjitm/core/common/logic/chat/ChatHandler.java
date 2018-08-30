package com.twjitm.core.common.logic.chat;


import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.entity.chat.ChatMessage;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.logic.handler.AbstractBaseHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;

public abstract class ChatHandler extends AbstractBaseHandler {

    @MessageCommandAnnotation(messageCmd = MessageComm.PRIVATE_CHAT_MESSAGE)
    public AbstractNettyNetProtoBufMessage chatMessage(ChatMessage chatMessage) {
        System.out.println("实现代理注解方法了");
        return chatMessageImpl(chatMessage);
    }


    public abstract AbstractNettyNetProtoBufMessage chatMessageImpl(ChatMessage chatMessage);

}
