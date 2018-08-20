package com.twjitm.core.common.config.global;

import com.twjitm.core.common.config.rpc.RpcServerConfig;
import com.twjitm.core.common.service.IService;
import org.jdom2.DataConversionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 13:27
 * https://blog.csdn.net/baidu_23086307
 */
@Service
public class NettyGameServiceConfigService implements IService {
    @Resource
    NettyGameServiceConfig nettyGameServiceConfig;
    @Resource
    ZookeeperConfig zookeeperConfig;
    @Resource
    RpcServerConfig rpcServerConfig;



    public NettyGameServiceConfig getNettyGameServiceConfig() {
        return nettyGameServiceConfig;
    }

    public ZookeeperConfig getZookeeperConfig() {
        return zookeeperConfig;
    }

    public RpcServerConfig getRpcServerConfig() {
        return rpcServerConfig;
    }

    @Override
    public String getId() {
        return NettyGameServiceConfigService.class.getSimpleName();
    }

    @Override
    public void startup() throws Exception {
        initRpcServer();
    }

    void initRpcServer(){
        try {
            rpcServerConfig.init();

        } catch (DataConversionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() throws Exception {

    }
}
