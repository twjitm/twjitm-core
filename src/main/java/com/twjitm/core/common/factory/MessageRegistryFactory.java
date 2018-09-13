package com.twjitm.core.common.factory;


import com.twjitm.core.common.annotation.MessageCommandAnnotation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.logic.handler.AbstractBaseHandler;
import com.twjitm.core.common.logic.handler.BaseHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.utils.PackageScaner;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 文江 on 2017/11/11.
 * 消息注册工厂
 */

/**
 * 原理：利用class类加载器加载编译好的消息字节码加载到内存中
 */
public class MessageRegistryFactory {
    Logger logger = LoggerUtils.getLogger(MessageRegistryFactory.class);
    private PackageScaner packageScaner = new PackageScaner();

    private Map<Integer, MessageComm> messageCommMap = new ConcurrentHashMap<Integer, MessageComm>();

    private Map<Integer, Class<? extends AbstractNettyNetProtoBufMessage>> messages = new HashMap<Integer, Class<? extends AbstractNettyNetProtoBufMessage>>();

    private String namespace;
    /**
     * logic handler map
     */
    private Map<Integer, BaseHandler> logicHandlerMap = new ConcurrentHashMap<>();

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void putMessages(int key, Class message) {
        messages.put(key, message);
    }

    public AbstractNettyNetProtoBufMessage get(int commId) {
        if (commId < 0) {
            throw new RuntimeException();
        }
        Class<? extends AbstractNettyNetProtoBufMessage> clzz = messages.get(commId);
        if (clzz == null) {
            return null;
        }
        try {
            AbstractNettyNetProtoBufMessage message = clzz.newInstance();
            //message.setCommId(commId);
            return message;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO 需优化
    public void init() {
        //init message
        loadPackage(namespace, ".class");
        //init handler
        loadPackage("com.twjitm.core.*.*");
        //init message enum
        loadMessageCommId();
        // loadPackage("com.twjitm.core.common.entity.online",".class");
    }

    public void loadPackage(String namespace, String suffix) {
       /* String[] fileNames = PackageScaner.scanNamespaceFiles(namespace, suffix, false, true);

        // 加载class,获取协议命令
        for (String fileName : fileNames) {
            String realClass = namespace
                    + "."
                    + fileName.subSequence(0, fileName.length()
                    - (suffix.length()));
            Class<?> messageClass = null;
            try {
                messageClass = Class.forName(realClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            MessageCommandAnnotation annotation = (MessageCommandAnnotation) messageClass
                    .getAnnotation(MessageCommandAnnotation.class);
            if (annotation != null && annotation.messageCmd() != null) {
                putMessages(annotation.messageCmd().commId, messageClass);
            }
        }
        */
        List<Class> list = PackageScaner.getSubClasses(AbstractNettyNetProtoBufMessage.class, "com.twjitm.core.*.*");
        for (Class messageClass : list) {
            MessageCommandAnnotation annotation = (MessageCommandAnnotation) messageClass
                    .getAnnotation(MessageCommandAnnotation.class);
            if (annotation != null && annotation.messageCmd() != null) {
                if(logger.isInfoEnabled()){
                    logger.info("REGISTER MESSAGE SUCCESSFUL COMM ID IS= "+annotation.messageCmd().commId+" ON CLASS "+messageClass);
                }
                putMessages(annotation.messageCmd().commId, messageClass);
            }
        }

    }

    public MessageComm getMessageComm(int commId) {
        return messageCommMap.get(commId);
    }

    private void loadMessageCommId() {
        MessageComm[] comms = MessageComm.values();
        for (MessageComm comm : comms) {
            messageCommMap.put(comm.commId, comm);
        }
    }

    //-----------------------handler

    /**
     * @param namespace
     */
    private void loadPackage(String namespace) {
        List<Class> list = PackageScaner.getSubClasses(AbstractBaseHandler.class, namespace);
        for (Class messageClass : list) {
            try {
                logger.info("HANDLER LOAD:" + messageClass.toString());
                BaseHandler baseHandler = getBaseHandler(messageClass);
                AbstractBaseHandler abstractBaseHandler = (AbstractBaseHandler) baseHandler;
                abstractBaseHandler.init();
                Method[] methods = messageClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(MessageCommandAnnotation.class)) {
                        MessageCommandAnnotation messageCommandAnnotation = (MessageCommandAnnotation) method.getAnnotation(MessageCommandAnnotation.class);
                        if (messageCommandAnnotation != null && messageCommandAnnotation.messageCmd() != null) {
                            if(logger.isInfoEnabled()){
                                logger.info("REGISTER HANDLER SUCCESSFUL COMM ID= "+messageCommandAnnotation.messageCmd().commId+" ON CLASS  "+messageClass+
                                        "  AND HANDLER IS"+baseHandler);
                            }
                            addHandler(messageCommandAnnotation.messageCmd().commId, baseHandler);
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
    private void addHandler(int commId, BaseHandler handler) {
        logicHandlerMap.put(commId, handler);

    }

    /**
     * @param classes
     * @return
     */
    private BaseHandler getBaseHandler(Class<?> classes) {
        try {
            if (classes == null) {
                return null;
            }
            BaseHandler messageHandler = (BaseHandler) classes
                    .newInstance();
            return messageHandler;
        } catch (Exception e) {
            logger.info("GETBASEHANDLER - CLASSES=" + classes.getName() + "," + e);
        }
        return null;

    }

    public BaseHandler getHandler(int commId) {
        return logicHandlerMap.get(commId);
    }


}
