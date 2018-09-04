package com.twjitm.core.common.entity;

public interface IMessage {
    /**
     * SESSION_ID
     * @return
     */
    public long getSessionId();
    /**
     * 命令号
     * @return
     */
    public int getCommandId();

    /**
     * 序列号
     * @return
     */
    public int getSerial();

    /**
     * 玩家id
     */
    public long getUserId();

    /**
     * 释放
     */
    public void release();

    /**
     * 信息
     */
    public String toAllInfoString();
}
