package com.twjitm.core.service.dispatcher.Impl;


import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.logic.handler.BaseHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.service.test.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 文江 on 2018/4/27.
 * 注解分发器
 */
@Service
public class DispatcherServiceImpl implements IDispatcherService {

    @Resource
    private TestService testService;

    @Resource
    private MessageRegistryFactory messageRegistryFactory;

    public Map<Integer, BaseHandler> handlerMap = new HashMap<Integer, BaseHandler>();
    public String[] filesName;


    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetProtoBufMessage message) {
        int commId = message.getNetMessageHead().getCmd();
        BaseHandler baseHandler = handlerMap.get(commId);
        /*if (baseHandler == null) {
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
            }
            return baseMessage;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public String getMessage(String message) {
        testService.say();
        System.out.println(message);
        messageRegistryFactory.getMessageComm(1001);
        return message;
    }

}
