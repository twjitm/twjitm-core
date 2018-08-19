package com.twjitm.core.common.config.global;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 13:27
 * https://blog.csdn.net/baidu_23086307
 */
@Service
public class NettyGameServiceConfigService {
    @Resource
    NettyGameServiceConfig nettyGameServiceConfig;
    @Resource
    ZookeeperConfig zookeeperConfig;


    public NettyGameServiceConfig getNettyGameServiceConfig() {
        return nettyGameServiceConfig;
    }

    public ZookeeperConfig getZookeeperConfig() {
        return zookeeperConfig;
    }
}
