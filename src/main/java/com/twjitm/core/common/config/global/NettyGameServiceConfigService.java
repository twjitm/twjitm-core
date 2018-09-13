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
    private NettyGameServiceConfig nettyGameServiceConfig;
    @Resource
    private ZookeeperConfig zookeeperConfig;
    @Resource
    private RpcServerConfig rpcServerConfig;
    @Resource
    private NettyGameUdpConfig udpConfig;
    @Resource
    private NettyGameHttpConfig httpConfig;
    @Resource
    private NettyGameRpcConfig rpcNetConfig;
    @Resource
    private KafkaConfig kafkaConfig;


    public NettyGameRpcConfig getRpcNetConfig() {
        return rpcNetConfig;
    }

    public NettyGameServiceConfig getNettyGameServiceConfig() {
        return nettyGameServiceConfig;
    }

    public NettyGameUdpConfig getUdpConfig() {
        return udpConfig;
    }

    public NettyGameHttpConfig getHttpConfig() {
        return httpConfig;
    }

    public ZookeeperConfig getZookeeperConfig() {
        return zookeeperConfig;
    }

    public RpcServerConfig getRpcServerConfig() {
        return rpcServerConfig;
    }

    public KafkaConfig getKafkaConfig() {
        return kafkaConfig;
    }

    @Override
    public String getId() {
        return NettyGameServiceConfigService.class.getSimpleName();
    }

    @Override
    public void startup() throws Exception {
        initConfigServer();
    }

    void initConfigServer() {
        try {
            //-----------------------网络
            //tcp
            nettyGameServiceConfig.init();
            //udp
            udpConfig.init();
            //http
            httpConfig.init();
            //rpc
            rpcNetConfig.init();
            //-------------------rpc注册

            rpcServerConfig.init();
            zookeeperConfig.init();
            kafkaConfig.init();
        } catch (DataConversionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() throws Exception {

    }
}
