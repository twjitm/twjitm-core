package com.twjitm.core.spring;

import com.twjitm.core.common.check.NettyLifeCycleCheckService;
import com.twjitm.core.common.config.global.NettyGameServiceConfigService;
import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.factory.NettyRpcMethodRegistryFactory;
import com.twjitm.core.common.factory.NettyRpcRequestFactory;
import com.twjitm.core.common.factory.NettyTcpMessageFactory;
import com.twjitm.core.common.factory.thread.NettyRpcHandlerThreadPoolFactory;
import com.twjitm.core.common.factory.thread.async.poll.AsyncThreadService;
import com.twjitm.core.common.kafka.KafkaTaskType;
import com.twjitm.core.common.kafka.NettyKafkaProducerListener;
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
import com.twjitm.core.common.process.udp.NettyUdpOrderNetProtoMessageProcessor;
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
import com.twjitm.core.service.user.UserService;
import com.twjitm.core.test.AsyncExecutorService;
import com.twjitm.core.utils.uuid.LongIdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * spring bean
 *
 * @author
 * @date 2018/4/16
 */
@Service
public class SpringLoadServiceImpl implements IService {


    @Resource
    private AsyncExecutorService asyncExecutorService;

    //-----------------------------------------------------------------------------------------
    /**
     *
     */
    @Resource
    private NettyGameServiceConfigService nettyGameServiceConfigService;

    //-----------------------------------------------------------------------------------------
    /**
     *
     */
    @Resource
    private IDispatcherService dispatcherService;
    //------------------------------------------------------------------------------------------

    /**
     * netty
     */
    @Resource
    private MessageRegistryFactory messageRegistryFactory;

    /**
     * rpc
     */
    @Resource
    private NettyRpcHandlerThreadPoolFactory nettyRpcHandlerThreadPoolFactory;

    /**
     * rpc
     */
    @Resource
    NettyRpcRequestFactory nettyRpcRequestFactory;

    //------------------------------------------------------------------------------------------
    /**
     * http
     */
    @Resource
    private INettyNetProtoBufHttpMessageEncoderFactory nettyNetProtoBufHttpMessageEncoderFactory;

    /**
     * http
     */
    @Resource
    private INettyNetProtoBuffHttpToMessageDecoderFactory nettyNetProtoBuffHttpToMessageDecoderFactory;

    /**
     * tcp
     */
    @Resource
    private INettyNetProtoBufTcpMessageEncoderFactory nettyNetProtoBufTcpMessageEncoderFactory;

    /**
     * tcp
     */
    @Resource
    private INettyNetProtoBuffTCPToMessageDecoderFactory nettyNetProtoBuffTCPToMessageDecoderFactory;

    /**
     * upd
     **/
    @Resource
    private INettyNetProtoBufUdpMessageEncoderFactory nettyNetProtoBufUdpMessageEncoderFactory;
    /**
     * udp
     **/
    @Resource
    private INettyNetProtoBuffUDPToMessageDecoderFactory nettyNetProtoBuffUDPToMessageDecoderFactory;
    //---------------------------------------------------------------------------------------------------
    /**
     *
     **/
    @Resource
    private LongIdGenerator longIdGenerator;
    //-----------------------------------------------------------------------------------------------------
    /**
     *
     **/
    @Resource
    private NettyNetMessageProcessLogic nettyNetMessageProcessLogic;
    /**
     *
     */
    @Resource
    private NettyTcpNetProtoMessageProcess netProtoMessageProcess;

    /**
     * tcp
     */
    @Resource
    private NettyTcpMessageQueueExecutorProcessor nettyTcpMessageQueueExecutorProcessor;
    /**
     *
     */
    @Resource
    private NettyQueueMessageExecutorProcessor nettyQueueMessageExecutorProcessor;
    /**
     * udp
     */
    @Resource
    private NettyUdpNetProtoMessageProcessor nettyUdpNetProtoMessageProcessor;

    /**
     * netty udp
     */
    @Resource
    private NettyUdpOrderNetProtoMessageProcessor nettyUdpOrderNetProtoMessageProcessor;

    //------------------------------------------------------------------------------------------
    /**
     * session
     */
    @Resource
    private NettyTcpSessionBuilder nettyTcpSessionBuilder;

    //------------------------------------------------------------------------------------------
    /**
     *
     */
    @Resource
    private NettyChannelOperationServiceImpl netTcpSessionLoopUpService;
    /**
     *
     */
    @Resource
    private NettyGamePlayerFindServiceImpl nettyGamePlayerLoopUpService;

    @Resource
    private NettyRemoteRpcHandlerService nettyRemoteRpcHandlerService;

    /**
     * rpc
     */
    @Resource
    private NettyRpcMethodRegistryFactory nettyRpcMethodRegistryFactory;
    /**
     *
     */
    @Resource
    private AsyncThreadService asyncThreadService;

    /**
     * rpc
     */
    @Resource
    private NettyRPCFutureService nettyRPCFutureService;

    /**
     * rpc
     */
    @Resource
    private NettyRpcProxyService nettyRpcProxyService;
    /**
     * rpc
     */
    @Resource
    private NettyRpcClientConnectService nettyRpcClientConnectService;

    /**
     * zookeeper
     */
    @Resource
    private NettyZookeeperRpcServiceRegistryService nettyZookeeperRpcServiceRegistryService;
    /**
     * zookeeper
     */
    @Resource
    private NettyZookeeperRpcServiceDiscoveryService nettyZookeeperRpcServiceDiscoveryService;


    //------------------------------------------------------------------------------------------


    /**
     * tcp
     */
    @Resource
    private NettyTcpMessageFactory nettyTcpMessageFactory;
    //------------------------------------------------------------------------------------------
    /**
     * tcp
     */
    @Resource
    private NettyTcpServerPipeLineImpl nettyTcpServerPipeLine;
    /**
     * udp
     */
    @Resource
    private NettyUdpServerPipeLineImpl nettyUdpServerPipeLine;
    //-------------------------------------------------------------------------------------------
    /**
     * http handler
     */
    @Resource
    private AsyncNettyHttpHandlerService asyncNettyHttpHandlerService;

    //-------------------------------------------------------------------------------------------

    /**
     * @return
     */
    @Resource
    private NettyProtoBufRpcSerialize nettyProtoBufRpcSerialize;
    //-----------------------------------------------------------------------------------------
    /**
     *
     */
    @Resource
    private NettyLifeCycleCheckService nettyLifeCycleCheckService;

    /**
     *
     */
    @Resource
    private NettyKafkaProducerListener nettyKafkaProducerListener;

    public NettyKafkaProducerListener getNettyKafkaProducerListener() {
        return nettyKafkaProducerListener;
    }

    public AsyncExecutorService getAsyncExecutorService() {
        return asyncExecutorService;
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

    public NettyUdpOrderNetProtoMessageProcessor getNettyUdpOrderNetProtoMessageProcessor() {
        return nettyUdpOrderNetProtoMessageProcessor;
    }

    public NettyLifeCycleCheckService getNettyLifeCycleCheckService() {
        return nettyLifeCycleCheckService;
    }

    @Override
    public String getId() {
        return SpringLoadServiceImpl.class.getSimpleName();
    }

    /**
     * @throws Exception
     */
    @Override
    public void startup() throws Exception {
        nettyGameServiceConfigService.startup();
        netTcpSessionLoopUpService.startup();
        nettyTcpMessageQueueExecutorProcessor.startup();
        nettyQueueMessageExecutorProcessor.startup();
        nettyUdpOrderNetProtoMessageProcessor.startup();
        asyncNettyHttpHandlerService.startup();
        nettyRpcMethodRegistryFactory.startup();
        asyncThreadService.startup();
        nettyRemoteRpcHandlerService.startup();
        nettyRPCFutureService.startup();
        nettyRpcProxyService.startup();
        nettyRpcClientConnectService.startup();
        nettyZookeeperRpcServiceRegistryService.startup();
        nettyZookeeperRpcServiceDiscoveryService.startup();
        nettyLifeCycleCheckService.startup();
        nettyKafkaProducerListener.startup();
    }


    @Override
    public void shutdown() throws Exception {
        netTcpSessionLoopUpService.shutdown();
        nettyTcpMessageQueueExecutorProcessor.shutdown();
        asyncNettyHttpHandlerService.shutdown();
        nettyRpcMethodRegistryFactory.shutdown();
        asyncThreadService.shutdown();
        nettyRPCFutureService.shutdown();
        nettyRpcProxyService.shutdown();
        nettyRpcClientConnectService.shutdown();
        nettyGameServiceConfigService.shutdown();
        nettyZookeeperRpcServiceRegistryService.shutdown();
        nettyZookeeperRpcServiceDiscoveryService.shutdown();
        nettyLifeCycleCheckService.shutdown();
        nettyKafkaProducerListener.shutdown();
    }


}
