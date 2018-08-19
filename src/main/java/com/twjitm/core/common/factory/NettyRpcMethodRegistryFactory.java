package com.twjitm.core.common.factory;

import com.twjitm.core.common.annotation.NettyRpcServiceAnnotation;
import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.config.global.NettyGameServiceConfig;
import com.twjitm.core.common.config.global.NettyGameServiceConfigService;
import com.twjitm.core.common.factory.classload.NettyClassloader;
import com.twjitm.core.common.service.IService;
import com.twjitm.core.common.service.rpc.serialize.NettyProtoBufRpcSerialize;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 13:12
 * https://blog.csdn.net/baidu_23086307
 */
public class NettyRpcMethodRegistryFactory implements IService {
    public static Logger logger = LoggerUtils.getLogger(NettyRpcMethodRegistryFactory.class);

    public NettyClassloader classScanner = new NettyClassloader();

    private ConcurrentHashMap<String, Object> nettyRpcServiceRegistryMap = new ConcurrentHashMap<>();

    @Override
    public String getId() {
        return "NettyRpcMethodRegistryFactory";
    }

    @Override
    public void startup() throws Exception {
        NettyGameServiceConfigService config = SpringServiceManager.getSpringLoadService().getNettyGameServiceConfigService();
        NettyGameServiceConfig serviceConfig = config.getNettyGameServiceConfig();
        loadPackage(serviceConfig.getRpcServicePackageName(),GlobalConstants.ConfigFile.NETTY_FILE_EXT);
    }

    @Override
    public void shutdown() throws Exception {

    }

    public void loadPackage(String namespace, String ext) throws Exception {
        String[] fileNames = classScanner.scannerPackage(namespace, ext);
        // 加载class,获取协议命令
        if(fileNames != null) {
            for (String fileName : fileNames) {
                String realClass = namespace
                        + "."
                        + fileName.subSequence(0, fileName.length()
                        - (ext.length()));
                Class<?> messageClass = Class.forName(realClass);

                logger.info("RPC LOAD:" + messageClass);
                NettyRpcServiceAnnotation nettyRpcServiceAnnotation = messageClass.getAnnotation(NettyRpcServiceAnnotation.class);
                if(nettyRpcServiceAnnotation != null) {
                    String interfaceName = messageClass.getAnnotation(NettyRpcServiceAnnotation.class).value().getName();
                    NettyProtoBufRpcSerialize rpcSerialize = SpringServiceManager.getSpringLoadService().getNettyProtoBufRpcSerialize();
                    Object serviceBean =rpcSerialize.newInstance(messageClass);
                    nettyRpcServiceRegistryMap.put(interfaceName, serviceBean);
                    logger.info("RPC REGISTER:" + messageClass);
                }
            }
        }
    }

    public Object getServiceBean(String className){
        return nettyRpcServiceRegistryMap.get(className);
    }

}
