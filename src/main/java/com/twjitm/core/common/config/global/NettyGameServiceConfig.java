package com.twjitm.core.common.config.global;

import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 13:25
 * https://blog.csdn.net/baidu_23086307
 */
@Service
public class NettyGameServiceConfig {
    /**rpc服务的包名字*/
    private String rpcServicePackageName;

    public void setRpcServicePackageName(String rpcServicePackageName) {
        this.rpcServicePackageName = rpcServicePackageName;
    }

    public String getRpcServicePackageName() {
        return rpcServicePackageName;
    }
}
