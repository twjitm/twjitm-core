package com.twjitm.core.common.logic.chat.Impl;


import com.twjitm.core.common.entity.chat.ChatMessage;
import com.twjitm.core.common.logic.chat.ChatHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;

public class ChatHandlerImpl extends ChatHandler {
    //业务逻辑代码了
    public AbstractNettyNetProtoBufMessage chatMessageImpl(ChatMessage chatMessage) {
        // chatMessage.getSendUid();
        System.out.println("注解分离消息机制----------");
        System.out.println(chatMessage.getContext());
        ChatMessage message=new ChatMessage();
        message=chatMessage;
        message.setContext("hello client");
        return message;
    }

}
