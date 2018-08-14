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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by twjitm on 2018/4/27.
 * 分发器
 */
@Service
public class DispatcherServiceImpl implements IDispatcherService {

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

    @Override
    public String getMessage(String message) {
        testService.say();
        System.out.println(message);
        messageRegistryFactory.getMessageComm(1001);
        return message;
    }

    /**
     * TODO
     */
    @PostConstruct
    public void init() {
        System.out.println("---------------------message handler:init logic handler iml start-----------------");
        loadPackage("com.twjitm.core.common.logic.chat.Impl",".class");
        loadPackage("com.twjitm.core.common.logic.online.Impl",".class");
    }
    /**
     *
     *
     * @param namespace
     * @param suffix
     */
    public void loadPackage(String namespace, String suffix) {
        if (filesName == null) {
            filesName = PackageScaner.scanNamespaceFiles(namespace, suffix, false, true);
        }
        DynamicGameClassLoader classLoader = new DynamicGameClassLoader();

       // URL url = ClassLoader.getSystemClassLoader().getResource("twjitm");
        for (int i = 0; i < filesName.length; i++) {
            String realClass = namespace
                    + "."
                    + filesName[i].subSequence(0, filesName[i].length()
                    - (suffix.length()));
            Class messageClass = null;
         //   File classFile = new File(url.getPath());
            try {
               // FileClassLoader fileClassLoader = new FileClassLoader(classFile);
               // byte[] classFileDate = fileClassLoader.getClassData(realClass);
                messageClass =  Class.forName(realClass);;//classLoader.findClass(realClass, classFileDate);
                System.out.println("handler load:" + messageClass.toString());
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
        filesName=null;
    }

    /**
     * ����handler
     *
     * @param commId
     * @param handler
     */
    public void addHandler(int commId, BaseHandler handler) {
        handlerMap.put(commId, handler);

    }

    /**
     * ͨ��class�����ȡ�������
     *
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
            System.err.println("getBaseHandler - classes=" + classes.getName() + "," + e);
        }
        return null;

    }


}
