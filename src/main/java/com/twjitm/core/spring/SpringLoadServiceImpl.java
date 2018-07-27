package com.twjitm.core.spring;

import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.factory.NettyTcpMessageFactory;
import com.twjitm.core.common.netstack.builder.NettyTcpSessionBuilder;
import com.twjitm.core.common.netstack.coder.decode.http.INettyNetProtoBuffHttpToMessageDecoderFactory;
import com.twjitm.core.common.netstack.coder.decode.tcp.INettyNetProtoBuffTCPToMessageDecoderFactory;
import com.twjitm.core.common.netstack.coder.encode.http.INettyNetProtoBufHttpMessageEncoderFactory;
import com.twjitm.core.common.netstack.coder.encode.tcp.INettyNetProtoBufTcpMessageEncoderFactory;
import com.twjitm.core.common.process.NettyNetMessageProcessLogic;
import com.twjitm.core.common.service.INettyChannleOperationService;
import com.twjitm.core.common.service.IService;
import com.twjitm.core.common.service.Impl.NettyChannelOperationServiceImpl;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.service.test.TestService;
import com.twjitm.core.service.user.UserService;
import com.twjitm.core.utils.uuid.LongIdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
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
    @Resource
    private IDispatcherService dispatcherService;
    @Resource
    private MessageRegistryFactory messageRegistryFactory;


    /**http编码器*/
    @Resource
    private INettyNetProtoBufHttpMessageEncoderFactory nettyNetProtoBufHttpMessageEncoderFactory;

    /**http解码器*/
     @Resource
    private INettyNetProtoBuffHttpToMessageDecoderFactory nettyNetProtoBuffHttpToMessageDecoderFactory;

     /**tcp协议编码器*/
     @Resource
     private INettyNetProtoBufTcpMessageEncoderFactory nettyNetProtoBufTcpMessageEncoderFactory;

    /**tcp协议解码器*/
    @Resource
    private INettyNetProtoBuffTCPToMessageDecoderFactory nettyNetProtoBuffTCPToMessageDecoderFactory;
    /**原子id生成器**/
    @Resource
    private LongIdGenerator longIdGenerator;
    /**消息处理bean**/
    @Resource
    private NettyNetMessageProcessLogic nettyNetMessageProcessLogic;


    @Resource
    private NettyTcpSessionBuilder nettyTcpSessionBuilder;
    @Resource
    private NettyChannelOperationServiceImpl netTcpSessionLoopUpService;
    @Resource
    private NettyTcpMessageFactory nettyTcpMessageFactory;


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



    @Override
    public String getId() {
        return "";
    }

    @Override
    public void startup() throws Exception {
        netTcpSessionLoopUpService.startup();
    }

    @Override
    public void shutdown() throws Exception {
        netTcpSessionLoopUpService.shutdown();
    }
}
