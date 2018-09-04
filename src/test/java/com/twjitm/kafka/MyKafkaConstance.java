package com.twjitm.kafka;

/**
 * Created by Marvin on 2017/9/21.
 */
public class MyKafkaConstance {

    /*
    发送到聊天服务器：SYSTEM_MSG WORLD_MSG PRIVATE_MSG UNION_MSG
     */
    public static final String SYSTEM_MSG = "system_msg";

    public static final String WORLD_MSG = "world_msg";

    public static final String PRIVATE_MSG = "private_msg";

    public static final String UNION_MSG = "union_msg";

    /**
     * 发送到GameSer UNION_SYSTEM_MSG TLOG_MSG FRIEND_MSG FAMILY_MSG ACTIVITY_MSG
     */

    public static final String UNION_SYSTEM_MSG = "push#union_system_msg";

    public static final String TLOG_MSG = "tlog_msg";

    public static final String FRIEND_MSG = "push#friend_diff_core_msg";

    public static final String FAMILY_MSG = "push#family_diff_core_msg";

    public static final String ACTIVITY_MSG = "push#activity_msg";

    public static final String TEAM_ATTACK_MSG = "push#team_attack_msg";

}
