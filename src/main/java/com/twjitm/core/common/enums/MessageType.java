package com.twjitm.core.common.enums;

/**
 * 消息类型处理
 */
public interface MessageType {
    /**
     * 聊天消息类型
     */
    public int CHAT_MESSAGE = 0;
    /**
     * 群聊
     */
    public int PUBLIC_CHART_MESSAGE = 1;
    /**
     * 单聊
     */
    public int PRIVATE_CHAT_MESSAGE = 2;
    /**
     * 玩家登录
     */
    public int PLAYER_LOGIN_MESSAGE = 3;
    /**
     * 玩家退出登录
     */
    public int PLAYER_LOGOUT_MESSAGE = 4;
    /**
     * 心跳协议
     */
    public int HEART_MESSAGE = 5;
}
