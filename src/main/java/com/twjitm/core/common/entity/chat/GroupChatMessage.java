package com.twjitm.core.common.entity.chat;

import com.alibaba.fastjson.JSON;

/**
 * Created by 文江 on 2017/11/5.
 */
public class GroupChatMessage extends ChatMessage {
    private long groupId;



    public GroupChatMessage(String json) {

        GroupChatMessage chatMessage = (GroupChatMessage) JSON.parse(json);
        this.groupId = chatMessage.getGroupId();

    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
