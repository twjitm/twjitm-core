package com.twjitm.core.spring;

import com.twjitm.core.common.config.global.NettyGameServiceConfigService;
import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.factory.NettyRpcMethodRegistryFactory;
import com.twjitm.core.common.factory.NettyRpcRequestFactory;
import com.twjitm.core.common.factory.NettyTcpMessageFactory;
import com.twjitm.core.common.factory.thread.NettyRpcHandlerThreadPoolFactory;
import com.twjitm.core.common.factory.thread.async.poll.AsyncThreadService;
import com.twjitm.core.common.netstack.builder.NettyTcpSessionBuilder;
import com.twjitm.core.common.netstack.coder.decode.http.INettyNetProtoBuffHttpToMessageDecoderFactory;
import com.twjitm.core.common.netstack.coder.decode.tcp.INettyNetProtoBuffTCPToMessageDecoderFactory;
import com.twjitm.core.common.netstack.coder.decode.udp.INettyNetProtoBuffUDPToMessageDecoderFactory;
import com.twjitm.core.common.netstack.coder.encode.http.INettyNetProtoBufHttpMessageEncoderFactory;
import com.twjitm.core.common.netstack.coder.encode.tcp.INettyNetProtoBufTcpMessageEncoderFactory;
import com.twjitm.core.common.netstack.coder.encode.udp.INettyNetProtoBufUdpMessageEncoderFactory;
import com.twjitm.core.common.netstack.pipeline.NettyTcpServerPipeLineImpl;
import com.twjitm.core.common.netstack.pipeline.NettyUdpServerPipeLineImpl;
import com.twjitm.core.common.process.NettyNetMessageProcessLogic;
import com.twjitm.core.common.process.NettyQueueMessageExecutorProcessor;
import com.twjitm.core.common.process.tcp.INettyTcpNetProtoMessageProcess;
import com.twjitm.core.common.process.tcp.NettyTcpMessageQueueExecutorProcessor;
import com.twjitm.core.common.process.tcp.NettyTcpNetProtoMessageProcess;
import com.twjitm.core.common.process.udp.NettyUdpNetProtoMessageProcessor;
import com.twjitm.core.common.service.INettyChannleOperationService;
import com.twjitm.core.common.service.IService;
import com.twjitm.core.common.service.Impl.NettyChannelOperationServiceImpl;
import com.twjitm.core.common.service.Impl.NettyGamePlayerFindServiceImpl;
import com.twjitm.core.common.service.http.AsyncNettyHttpHandlerService;
import com.twjitm.core.common.service.rpc.serialize.NettyProtoBufRpcSerialize;
import com.twjitm.core.common.service.rpc.service.NettyRPCFutureService;
import com.twjitm.core.common.service.rpc.service.NettyRemoteRpcHandlerService;
import com.twjitm.core.common.service.rpc.service.NettyRpcClientConnectService;
import com.twjitm.core.common.service.rpc.service.NettyRpcProxyService;
import com.twjitm.core.common.zookeeper.NettyZookeeperRpcServiceDiscoveryService;
import com.twjitm.core.common.zookeeper.NettyZookeeperRpcServiceRegistryService;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.service.test.TestService;
import com.twjitm.core.service.user.UserService;
import com.twjitm.core.utils.uuid.LongIdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 文江
 * @date 2018/4/16
 * 通过spring bean配置
 */
@Service
public class SpringLoadServiceImpl implements IService {
    @Resource
    private TestService testService;
    @Resource
    private UserService userService;

    //-----------------------------------------------------------------------------------------
    /**
     * 配置服务器
     */
    @Resource
    NettyGameServiceConfigService nettyGameServiceConfigService;

    //-----------------------------------------------------------------------------------------
    /**
     * 分发器服务
     */
    @Resource
    private IDispatcherService dispatcherService;
    //------------------------------------------------------------------------------------------

    /**
     * netty消息注册工厂
     */
    @Resource
    private MessageRegistryFactory messageRegistryFactory;

    /**
     * rpc线程工厂
     */
    @Resource
    private NettyRpcHandlerThreadPoolFactory nettyRpcHandlerThreadPoolFactory;

    /**
     * rpc 请求消息构造工厂
     */
    @Resource
    NettyRpcRequestFactory nettyRpcRequestFactory;

    //------------------------------------------------------------------------------------------
    /**
     * http编码器
     */
    @Resource
    private INettyNetProtoBufHttpMessageEncoderFactory nettyNetProtoBufHttpMessageEncoderFactory;

    /**
     * http解码器
     */
    @Resource
    private INettyNetProtoBuffHttpToMessageDecoderFactory nettyNetProtoBuffHttpToMessageDecoderFactory;

    /**
     * tcp协议编码器
     */
    @Resource
    private INettyNetProtoBufTcpMessageEncoderFactory nettyNetProtoBufTcpMessageEncoderFactory;

    /**
     * tcp协议解码器
     */
    @Resource
    private INettyNetProtoBuffTCPToMessageDecoderFactory nettyNetProtoBuffTCPToMessageDecoderFactory;

    /**
     * upd协议编码器工厂
     **/
    @Resource
    private INettyNetProtoBufUdpMessageEncoderFactory nettyNetProtoBufUdpMessageEncoderFactory;
    /**
     * udp协议解码器
     **/
    @Resource
    private INettyNetProtoBuffUDPToMessageDecoderFactory nettyNetProtoBuffUDPToMessageDecoderFactory;
    //---------------------------------------------------------------------------------------------------
    /**
     * 原子id生成器
     **/
    @Resource
    private LongIdGenerator longIdGenerator;
    //-----------------------------------------------------------------------------------------------------
    /**
     * 消息处理bean
     **/
    @Resource
    private NettyNetMessageProcessLogic nettyNetMessageProcessLogic;
    /**
     * 真正最终消息执行器
     */
    @Resource
    private NettyTcpNetProtoMessageProcess netProtoMessageProcess;

    /**
     * tcp消息单独--处理器
     */
    @Resource
    private NettyTcpMessageQueueExecutorProcessor nettyTcpMessageQueueExecutorProcessor;
    /**
     * 系统内部消息处理队列
     */
    @Resource
    private NettyQueueMessageExecutorProcessor nettyQueueMessageExecutorProcessor;
    /**
     * udp协议消息处理---生产者-消费者
     */
    @Resource
    private NettyUdpNetProtoMessageProcessor nettyUdpNetProtoMessageProcessor;

    //------------------------------------------------------------------------------------------
    /**
     * session绑定器
     */
    @Resource
    private NettyTcpSessionBuilder nettyTcpSessionBuilder;

    //------------------------------------------------------------------------------------------
    //----------------------基础服务类--------------------------------------------------------
    /**
     * 查询器 netty的channel 和自定义session的查询
     */
    @Resource
    private NettyChannelOperationServiceImpl netTcpSessionLoopUpService;
    /**
     * 查询器 自定义session和自定义player实体绑定查询
     */
    @Resource
    private NettyGamePlayerFindServiceImpl nettyGamePlayerLoopUpService;

    @Resource
    private NettyRemoteRpcHandlerService nettyRemoteRpcHandlerService;

    /**
     * rpc消息注解器
     */
    @Resource
    private NettyRpcMethodRegistryFactory nettyRpcMethodRegistryFactory;
    /**
     * 异步线程操作
     */
    @Resource
    private AsyncThreadService asyncThreadService;

    /**
     * rpc 异步消息调用类
     */
    @Resource
    private NettyRPCFutureService nettyRPCFutureService;

    /**
     * rpc 代理服务
     */
    @Resource
    private NettyRpcProxyService nettyRpcProxyService;
    /**
     * rpc 服务发现
     */
    @Resource
    private NettyRpcClientConnectService nettyRpcClientConnectService;

    /**
     * zookeeper 注册器
     */
    @Resource
    private NettyZookeeperRpcServiceRegistryService nettyZookeeperRpcServiceRegistryService;
    /**
     * zookeeper 发现器
     */
    @Resource
    private NettyZookeeperRpcServiceDiscoveryService nettyZookeeperRpcServiceDiscoveryService;


    //------------------------------------------------------------------------------------------


    /**
     * tcp消息工厂
     */
    @Resource
    private NettyTcpMessageFactory nettyTcpMessageFactory;
    //------------------------------------------------------------------------------------------
    /**
     * tcp管道
     */
    @Resource
    private NettyTcpServerPipeLineImpl nettyTcpServerPipeLine;
    /**
     * udp管道
     */
    @Resource
    private NettyUdpServerPipeLineImpl nettyUdpServerPipeLine;
    //-------------------------------------------------------------------------------------------
    @Resource
    private AsyncNettyHttpHandlerService asyncNettyHttpHandlerService;

    //-------------------------------------------------------------------------------------------

    /**
     * 序列化服务
     *
     * @return
     */
    @Resource
    private NettyProtoBufRpcSerialize nettyProtoBufRpcSerialize;


    public TestService getTestService() {
        return testService;
    }

    public UserService getUserService() {
        return userService;
    }

    public IDispatcherService getDispatcherService() {
        return dispatcherService;
    }

    public MessageRegistryFactory getMessageRegistryFactory() {
        return messageRegistryFactory;
    }

    public INettyNetProtoBufHttpMessageEncoderFactory getNettyNetProtoBufHttpMessageEncoderFactory() {
        return nettyNetProtoBufHttpMessageEncoderFactory;
    }

    public INettyNetProtoBuffHttpToMessageDecoderFactory getNettyNetProtoBuffHttpToMessageDecoderFactory() {
        return nettyNetProtoBuffHttpToMessageDecoderFactory;
    }

    public INettyNetProtoBufTcpMessageEncoderFactory getNettyNetProtoBufTcpMessageEncoderFactory() {
        return nettyNetProtoBufTcpMessageEncoderFactory;
    }

    public INettyNetProtoBuffTCPToMessageDecoderFactory getNettyNetProtoBuffTCPToMessageDecoderFactory() {
        return nettyNetProtoBuffTCPToMessageDecoderFactory;
    }

    public LongIdGenerator getLongIdGenerator() {
        return longIdGenerator;
    }

    public NettyNetMessageProcessLogic getNettyNetMessageProcessLogic() {
        return nettyNetMessageProcessLogic;
    }

    public NettyTcpSessionBuilder getNettyTcpSessionBuilder() {
        return nettyTcpSessionBuilder;
    }

    public INettyChannleOperationService getNetTcpSessionLoopUpService() {
        return netTcpSessionLoopUpService;
    }

    public NettyTcpMessageFactory getNettyTcpMessageFactory() {
        return nettyTcpMessageFactory;
    }

    public INettyNetProtoBufUdpMessageEncoderFactory getNettyNetProtoBufUdpMessageEncoderFactory() {
        return nettyNetProtoBufUdpMessageEncoderFactory;
    }

    public INettyNetProtoBuffUDPToMessageDecoderFactory getNettyNetProtoBuffUDPToMessageDecoderFactory() {
        return nettyNetProtoBuffUDPToMessageDecoderFactory;
    }

    public NettyTcpServerPipeLineImpl getNettyTcpServerPipeLine() {
        return nettyTcpServerPipeLine;
    }

    public INettyTcpNetProtoMessageProcess getNetProtoMessageProcess() {
        return netProtoMessageProcess;
    }

    public NettyTcpMessageQueueExecutorProcessor getNettyTcpMessageQueueExecutorProcessor() {
        return nettyTcpMessageQueueExecutorProcessor;
    }

    public NettyUdpServerPipeLineImpl getNettyUdpServerPipeLine() {
        return nettyUdpServerPipeLine;
    }


    public NettyGamePlayerFindServiceImpl getNettyGamePlayerLoopUpService() {
        return nettyGamePlayerLoopUpService;
    }

    public NettyQueueMessageExecutorProcessor getNettyQueueMessageExecutorProcessor() {
        return nettyQueueMessageExecutorProcessor;
    }

    public NettyUdpNetProtoMessageProcessor getNettyUdpNetProtoMessageProcessor() {
        return nettyUdpNetProtoMessageProcessor;
    }

    public AsyncNettyHttpHandlerService getAsyncNettyHttpHandlerService() {
        return asyncNettyHttpHandlerService;
    }

    public NettyProtoBufRpcSerialize getNettyProtoBufRpcSerialize() {
        return nettyProtoBufRpcSerialize;
    }

    public NettyRpcHandlerThreadPoolFactory getNettyRpcHandlerThreadPoolFactory() {
        return nettyRpcHandlerThreadPoolFactory;
    }

    public NettyRemoteRpcHandlerService getNettyRemoteRpcHandlerService() {
        return nettyRemoteRpcHandlerService;
    }

    public NettyGameServiceConfigService getNettyGameServiceConfigService() {
        return nettyGameServiceConfigService;
    }

    public NettyRpcMethodRegistryFactory getNettyRpcMethodRegistryFactory() {
        return nettyRpcMethodRegistryFactory;
    }

    public AsyncThreadService getAsyncThreadService() {
        return asyncThreadService;
    }

    public NettyRPCFutureService getNettyRPCFutureService() {
        return nettyRPCFutureService;
    }

    public NettyRpcProxyService getNettyRpcProxyService() {
        return nettyRpcProxyService;
    }

    public NettyRpcClientConnectService getNettyRpcClientConnectService() {
        return nettyRpcClientConnectService;
    }

    public NettyRpcRequestFactory getNettyRpcRequestFactory() {
        return nettyRpcRequestFactory;
    }

    public NettyZookeeperRpcServiceRegistryService getNettyZookeeperRpcServiceRegistryService() {
        return nettyZookeeperRpcServiceRegistryService;
    }

    public NettyZookeeperRpcServiceDiscoveryService getNettyZookeeperRpcServiceDiscoveryService() {
        return nettyZookeeperRpcServiceDiscoveryService;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public void startup() throws Exception {
        nettyGameServiceConfigService.startup();

        netTcpSessionLoopUpService.startup();
        nettyTcpMessageQueueExecutorProcessor.start();
        nettyQueueMessageExecutorProcessor.start();
        asyncNettyHttpHandlerService.startup();
        nettyRpcMethodRegistryFactory.startup();
        asyncThreadService.startup();
        nettyRemoteRpcHandlerService.startup();
        nettyRPCFutureService.startup();
        nettyRpcProxyService.startup();
        nettyRpcClientConnectService.startup();
        nettyZookeeperRpcServiceRegistryService.startup();
        nettyZookeeperRpcServiceDiscoveryService.startup();

    }

    @Override
    public void shutdown() throws Exception {
        netTcpSessionLoopUpService.shutdown();
        nettyTcpMessageQueueExecutorProcessor.stop();
        asyncNettyHttpHandlerService.shutdown();
        nettyRpcMethodRegistryFactory.shutdown();
        asyncThreadService.shutdown();
        nettyRPCFutureService.shutdown();
        nettyRpcProxyService.shutdown();
        nettyRpcClientConnectService.shutdown();
        nettyGameServiceConfigService.shutdown();
        nettyZookeeperRpcServiceRegistryService.shutdown();
        nettyZookeeperRpcServiceDiscoveryService.shutdown();
    }


}
