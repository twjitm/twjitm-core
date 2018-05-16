package com.twjitm.core.common.dispatcher;


import com.twjitm.core.common.annotation.MessageCommandAnntation;
import com.twjitm.core.common.factory.MessageRegistryFactory;
import com.twjitm.core.common.factory.classload.DynamicGameClassLoader;
import com.twjitm.core.common.factory.classload.FileClassLoader;
import com.twjitm.core.common.logic.handler.AbstractBaseHandler;
import com.twjitm.core.common.logic.handler.BaseHandler;
import com.twjitm.core.common.manager.LocalManager;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.utils.PackageScaner;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于注解的分发器
 * 作者：twjitm
 */
public class Dispatcher implements IDispatcher {

    public Map<Integer, BaseHandler> handlerMap = new HashMap<Integer, BaseHandler>();
    ;
    public String[] filesName;


    public void dispatchAction(Channel channel, Object byteBuf) throws Exception {
        MessageRegistryFactory messageRegistryFactory = LocalManager.getInstance().getRegistryFactory();
        String date=((TextWebSocketFrame) byteBuf).text();
        short messageCommId = (short) AbstractNettyNetProtoBufMessage.getCmdToJson(date);
        AbstractNettyNetProtoBufMessage baseMessage = messageRegistryFactory.get(messageCommId);
        //baseMessage.decodeMessage(byteBuf);
        if(byteBuf instanceof TextWebSocketFrame){
            baseMessage.initNettyNetMessageHead(date);
            baseMessage.decoderNetJsonMessageBody(date);
            dispatcher(baseMessage);
        }
        if(byteBuf instanceof AbstractNettyNetProtoBufMessage){
            dispatcher((AbstractNettyNetProtoBufMessage) byteBuf);
        }

    }

    /**
     * 隔离器
     *
     * @param message
     * @return
     */
    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetProtoBufMessage message) {
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
     * 保存handler
     *
     * @param commId
     * @param handler
     */
    public void addHandler(int commId, BaseHandler handler) {
        handlerMap.put(commId, handler);

    }

    /**
     * 通过class 文件所在的名称空间，和后缀名加载class上的方法注解
     *
     * @param namespace
     * @param suffix
     */
    public void loadPackage(String namespace, String suffix) {
        if (filesName == null) {
            filesName = PackageScaner.scanNamespaceFiles(namespace, suffix, false, true);
        }
        DynamicGameClassLoader classLoader = new DynamicGameClassLoader();
        URL url = ClassLoader.getSystemClassLoader().getResource("");
        System.out.println(url.getPath());
        for (int i = 0; i < filesName.length; i++) {
            String realClass = namespace
                    + "."
                    + filesName[i].subSequence(0, filesName[i].length()
                    - (suffix.length()));
            Class messageClass = null;
            File classFile = new File(url.getPath());
            try {
                FileClassLoader fileClassLoader = new FileClassLoader(classFile);
                byte[] classFileDate = fileClassLoader.getClassData(realClass);
                messageClass = classLoader.findClass(realClass, classFileDate);
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
    }

    /**
     * 通过class对象获取反射的类
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

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        // System.out.println(MessageComm.MESSAGE_TRUE_RETURN.commId);
        dispatcher.loadPackage("com.twjitm.common.logic.chat.Impl", ".class");
    }


   /* public void test(){
        IReceiptService receiptService = LocalManager.getInstance().getSpringBeanManager().getReceiptService();
        receiptService.getReceiptByState(ReceiptStateType.DOING);
    }*/
}
