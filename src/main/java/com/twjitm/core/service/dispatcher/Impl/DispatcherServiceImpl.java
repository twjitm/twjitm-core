package com.twjitm.core.service.dispatcher.Impl;


import com.twjitm.core.common.factory.NettyRpcMethodRegistryFactory;
import com.twjitm.core.common.logic.handler.BaseHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.entity.rpc.NettyRpcRequestMessage;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author twjitm
 * @date 2018/4/27
 * 分发器 ,消息在上层通过消息队列，获取消息，然后进行处理
 * <p>
 * ------------------------------
 * msg1,mes2,msg3,msg4,msg5......
 * ------------------------------
 * head   /\                 last
 * |
 * Object object = method.invoke(baseHandler,message);
 * use proxy pattern progress message :tcp.udp stream in the dispatcher handler
 * and add rpc message dispatcher handler in this
 */
@Service
public class DispatcherServiceImpl implements IDispatcherService {
    private Logger logger = LoggerUtils.getLogger(DispatcherServiceImpl.class);


    @Override
    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetMessage message) {
        int commId = message.getNetMessageHead().getCmd();
        BaseHandler baseHandler = SpringServiceManager.getSpringLoadService().getMessageRegistryFactory()
                .getHandler(commId);
        if (baseHandler == null) {
            return null;
        }
        Method method = baseHandler.getMethod(commId);
        method.setAccessible(true);
        try {
            Object object = method.invoke(baseHandler,
                    message);
            AbstractNettyNetProtoBufMessage baseMessage = null;
            if (object != null) {
                baseMessage = (AbstractNettyNetProtoBufMessage) object;
            } else {
                baseMessage = (AbstractNettyNetProtoBufMessage) SpringServiceManager.getSpringLoadService().getNettyTcpMessageFactory().createCommonErrorResponseMessage(1, 1);
            }

            if (logger.isDebugEnabled()) {
                logger.info("INVOKE MESSAGE SUCCESSFUL MESSAGE COMM ID IS:" + commId);
            }
            return baseMessage;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        AbstractNettyNetMessage error = SpringServiceManager.getSpringLoadService().getNettyTcpMessageFactory().createCommonErrorResponseMessage(1, 1);
        return (AbstractNettyNetProtoBufMessage) error;
    }


    @Override
    public Object dispatcher(NettyRpcRequestMessage request) throws Throwable {
        String className = request.getClassName();
        NettyRpcMethodRegistryFactory factory = SpringServiceManager.getSpringLoadService().getNettyRpcMethodRegistryFactory();
        Object serviceBean = factory.getServiceBean(className);
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        if (logger.isInfoEnabled()) {
            logger.info(methodName);
            logger.info(serviceClass.getName());
            for (int i = 0; i < parameterTypes.length; ++i) {
                logger.debug(parameterTypes[i].getName());
            }
            for (int i = 0; i < parameters.length; ++i) {
                logger.info(parameters[i].toString());
            }
        }
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        return method.invoke(serviceBean, parameters);
    }

}
