package com.twjitm.core.common.netstack.entity.rpc;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 10:15
 * https://blog.csdn.net/baidu_23086307
 */
public class NettyRpcResponseMessage {
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 错误码
     */
    private String error;
    /**
     * 返回值
     */
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public String getError() {
        return error;
    }

    public Object getResult() {
        return result;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    public boolean isError() {
        return error != null;
    }
}
