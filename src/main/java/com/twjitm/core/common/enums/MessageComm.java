package com.twjitm.core.common.enums;

/**
 * Created by 文江 on 2017/11/5.
 */
public enum MessageComm {
    //tcp协议0-30000
    MESSAGE_TRUE_RETURN(0),
    PUBLIC_CHART_MESSAGE(1),
    PRIVATE_CHAT_MESSAGE(2),
    PLAYER_LOGIN_MESSAGE(3),
    PLAYER_LOGOUT_MESSAGE(4),
    DELETE_CHAT_MESSAGE(5),
    HEART_MESSAGE(6),



    //udp协议
    UDP_TEST_COMM(30001)
    ,;



    public int commId;

    MessageComm(int commId) {
        this.commId = commId;
    }

    public static int getVaule(MessageComm messageComm) {
        return messageComm.commId;
    }

}
