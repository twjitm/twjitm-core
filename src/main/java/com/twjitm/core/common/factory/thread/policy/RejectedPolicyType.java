package com.twjitm.core.common.factory.thread.policy;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 10:47
 * https://blog.csdn.net/baidu_23086307
 */
public enum  RejectedPolicyType {
    /**丢弃*/
    ABORT_POLICY("AbortPolicy"),
    /*阻塞*/
    BLOCKING_POLICY("BlockingPolicy"),
    /*直接运行*/
    CALLER_RUNS_POLICY("CallerRunsPolicy"),
    /*抛弃老的*/
    DISCARD_OLDEST_POLICY("DiscardOldestPolicy"),
    /*删除*/
    DISCARD_POLICY("DiscardPolicy");
    private String value;

    private RejectedPolicyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RejectedPolicyType fromString(String value) {
        for (RejectedPolicyType type : RejectedPolicyType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Mismatched type with value=" + value);
    }

    public String toString() {
        return value;
    }
}
