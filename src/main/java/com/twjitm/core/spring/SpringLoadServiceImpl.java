package com.twjitm.core.spring;

import com.twjitm.core.common.check.NettyLifeCycleCheckService;
import com.twjitm.core.common.config.global.NettyGameServiceConfigService;
import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.factory.NettyRpcMethodRegistryFactory;
import com.twjitm.core.common.factory.NettyRpcRequestFactory;
import com.twjitm.core.common.factory.NettyTcpMessageFactory;
import com.twjitm.core.common.factory.thread.NettyRpcHandlerThreadPoolFactory;
import com.twjitm.core.common.factory.thread.async.poll.AsyncThreadService;
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
 * @author �Ľ�
 * @date 2018/4/16
 * ͨ��spring bean����
 */
@Service
public class SpringLoadServiceImpl implements IService {

    @Resource
    private UserService userService;
    @Resource
    AsyncExecutorService asyncExecutorService;

    //-----------------------------------------------------------------------------------------
    /**
     * ���÷�����
     */
    @Resource
    NettyGameServiceConfigService nettyGameServiceConfigService;

    //-----------------------------------------------------------------------------------------
    /**
     * �ַ�������
     */
    @Resource
    private IDispatcherService dispatcherService;
    //------------------------------------------------------------------------------------------

    /**
     * netty��Ϣע�Ṥ��
     */
    @Resource
    private MessageRegistryFactory messageRegistryFactory;

    /**
     * rpc�̹߳���
     */
    @Resource
    private NettyRpcHandlerThreadPoolFactory nettyRpcHandlerThreadPoolFactory;

    /**
     * rpc ������Ϣ���칤��
     */
    @Resource
    NettyRpcRequestFactory nettyRpcRequestFactory;

    //------------------------------------------------------------------------------------------
    /**
     * http������
     */
    @Resource
    private INettyNetProtoBufHttpMessageEncoderFactory nettyNetProtoBufHttpMessageEncoderFactory;

    /**
     * http������
     */
    @Resource
    private INettyNetProtoBuffHttpToMessageDecoderFactory nettyNetProtoBuffHttpToMessageDecoderFactory;

    /**
     * tcpЭ�������
     */
    @Resource
    private INettyNetProtoBufTcpMessageEncoderFactory nettyNetProtoBufTcpMessageEncoderFactory;

    /**
     * tcpЭ�������
     */
    @Resource
    private INettyNetProtoBuffTCPToMessageDecoderFactory nettyNetProtoBuffTCPToMessageDecoderFactory;

    /**
     * updЭ�����������
     **/
    @Resource
    private INettyNetProtoBufUdpMessageEncoderFactory nettyNetProtoBufUdpMessageEncoderFactory;
    /**
     * udpЭ�������
     **/
    @Resource
    private INettyNetProtoBuffUDPToMessageDecoderFactory nettyNetProtoBuffUDPToMessageDecoderFactory;
    //---------------------------------------------------------------------------------------------------
    /**
     * ԭ��id������
     **/
    @Resource
    private LongIdGenerator longIdGenerator;
    //-----------------------------------------------------------------------------------------------------
    /**
     * ��Ϣ����bean
     **/
    @Resource
    private NettyNetMessageProcessLogic nettyNetMessageProcessLogic;
    /**
     * ����������Ϣִ����
     */
    @Resource
    private NettyTcpNetProtoMessageProcess netProtoMessageProcess;

    /**
     * tcp��Ϣ����--������
     */
    @Resource
    private NettyTcpMessageQueueExecutorProcessor nettyTcpMessageQueueExecutorProcessor;
    /**
     * ϵͳ�ڲ���Ϣ�������
     */
    @Resource
    private NettyQueueMessageExecutorProcessor nettyQueueMessageExecutorProcessor;
    /**
     * udpЭ����Ϣ����---������-������
     */
    @Resource
    private NettyUdpNetProtoMessageProcessor nettyUdpNetProtoMessageProcessor;

    /**
     * netty udp �첽����
     */
    @Resource
    private NettyUdpOrderNetProtoMessageProcessor nettyUdpOrderNetProtoMessageProcessor;

    //------------------------------------------------------------------------------------------
    /**
     * session����
     */
    @Resource
    private NettyTcpSessionBuilder nettyTcpSessionBuilder;

    //------------------------------------------------------------------------------------------
    //----------------------����������--------------------------------------------------------
    /**
     * ��ѯ�� netty��channel ���Զ���session�Ĳ�ѯ
     */
    @Resource
    private NettyChannelOperationServiceImpl netTcpSessionLoopUpService;
    /**
     * ��ѯ�� �Զ���session���Զ���playerʵ��󶨲�ѯ
     */
    @Resource
    private NettyGamePlayerFindServiceImpl nettyGamePlayerLoopUpService;

    @Resource
    private NettyRemoteRpcHandlerService nettyRemoteRpcHandlerService;

    /**
     * rpc��Ϣע����
     */
    @Resource
    private NettyRpcMethodRegistryFactory nettyRpcMethodRegistryFactory;
    /**
     * �첽�̲߳���
     */
    @Resource
    private AsyncThreadService asyncThreadService;

    /**
     * rpc �첽��Ϣ������
     */
    @Resource
    private NettyRPCFutureService nettyRPCFutureService;

    /**
     * rpc �������
     */
    @Resource
    private NettyRpcProxyService nettyRpcProxyService;
    /**
     * rpc ������
     */
    @Resource
    private NettyRpcClientConnectService nettyRpcClientConnectService;

    /**
     * zookeeper ע����
     */
    @Resource
    private NettyZookeeperRpcServiceRegistryService nettyZookeeperRpcServiceRegistryService;
    /**
     * zookeeper ������
     */
    @Resource
    private NettyZookeeperRpcServiceDiscoveryService nettyZookeeperRpcServiceDiscoveryService;


    //------------------------------------------------------------------------------------------


    /**
     * tcp��Ϣ����
     */
    @Resource
    private NettyTcpMessageFactory nettyTcpMessageFactory;
    //------------------------------------------------------------------------------------------
    /**
     * tcp�ܵ�
     */
    @Resource
    private NettyTcpServerPipeLineImpl nettyTcpServerPipeLine;
    /**
     * udp�ܵ�
     */
    @Resource
    private NettyUdpServerPipeLineImpl nettyUdpServerPipeLine;
    //-------------------------------------------------------------------------------------------
    /**
     * �첽http handler ����
     */
    @Resource
    private AsyncNettyHttpHandlerService asyncNettyHttpHandlerService;

    //-------------------------------------------------------------------------------------------

    /**
     * ���л�����
     *
     * @return
     */
    @Resource
    private NettyProtoBufRpcSerialize nettyProtoBufRpcSerialize;
    //-----------------------------------------------------------------------------------------
    /**
     * ��������Ϸ���
     */
    @Resource
    NettyLifeCycleCheckService nettyLifeCycleCheckService;

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
     * ˳���ܸġ������в���Ԥ֪��bug
     *
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
        test();

    }
    @Resource
    NettyKafkaProducerListener nettyKafkaProducerListener;
    private void test() {
        nettyKafkaProducerListener.sendMessage();


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
    }


}
