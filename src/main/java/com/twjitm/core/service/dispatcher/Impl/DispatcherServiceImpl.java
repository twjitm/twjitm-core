package com.twjitm.core.service.dispatcher.Impl;


import com.twjitm.core.common.annotation.MessageCommandAnntation;
import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.factory.classload.DynamicGameClassLoader;
import com.twjitm.core.common.logic.handler.AbstractBaseHandler;
import com.twjitm.core.common.logic.handler.BaseHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.utils.PackageScaner;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.service.test.TestService;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author twjitm
 * @date 2018/4/27
 * 分发器 ,消息在上层通过消息队列，获取消息，然后进行处理
 * <p>
 * ------------------------------
 * msg1,mes2,msg3,msg4,msg5......
 * ------------------------------
 * head                      last
 */
@Service
public class DispatcherServiceImpl implements IDispatcherService {
    private Logger logger = LoggerUtils.getLogger(DispatcherServiceImpl.class);

    @Resource
    private TestService testService;

    @Resource
    private MessageRegistryFactory messageRegistryFactory;

    public Map<Integer, BaseHandler> handlerMap = new HashMap<Integer, BaseHandler>();
    public String[] filesName;


    @Override
    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetMessage message) {
        int commId = message.getNetMessageHead().getCmd();
        BaseHandler baseHandler = handlerMap.get(commId);
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
            }
            return baseMessage;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * TODO
     */
    @PostConstruct
    public void init() {
        logger.info("---------------------message handler:init logic handler iml start-----------------");
        loadPackage("com.twjitm.core.*.*");
    }

    /**
     * @param namespace
     */
    public void loadPackage(String namespace) {
        List<Class> list = PackageScaner.getSubClasses(AbstractBaseHandler.class, namespace);
        for (Class messageClass : list) {
            try {
                logger.info("handler load:" + messageClass.toString());
                BaseHandler baseHandler = getBaseHandler(messageClass);
                AbstractBaseHandler abstractBaseHandler = (AbstractBaseHandler) baseHandler;
                abstractBaseHandler.init();
                Method[] methods = messageClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(MessageCommandAnntation.class)) {
                        MessageCommandAnntation messageCommandAnnotation = (MessageCommandAnntation) method.getAnnotation(MessageCommandAnntation.class);
                        if (messageCommandAnnotation != null && messageCommandAnnotation.messagecmd() != null) {
                            addHandler(messageCommandAnnotation.messagecmd().commId, baseHandler);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * add handler
     *
     * @param commId
     * @param handler
     */
    public void addHandler(int commId, BaseHandler handler) {
        handlerMap.put(commId, handler);

    }

    /**
     * @param classes
     * @return
     */
    public BaseHandler getBaseHandler(Class<?> classes) {
        try {
            if (classes == null) {
                return null;
            }
            BaseHandler messageHandler = (BaseHandler) classes
                    .newInstance();
            return messageHandler;
        } catch (Exception e) {
            logger.info("getBaseHandler - classes=" + classes.getName() + "," + e);
        }
        return null;

    }

    public static void main(String[] args) {
        List<Class> list = PackageScaner.getSubClasses(AbstractBaseHandler.class, "com.twjitm.core.*.*");
        for (Class clzz : list) {

        }


    }

}
