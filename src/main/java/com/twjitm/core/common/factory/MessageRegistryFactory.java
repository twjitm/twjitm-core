package com.twjitm.core.common.factory;


import com.twjitm.core.common.annotation.MessageCommandAnntation;
import com.twjitm.core.common.enums.MessageComm;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.utils.PackageScaner;

import java.util.HashMap;
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
    private PackageScaner packageScaner = new PackageScaner();

    private Map<Integer, MessageComm> messageCommMap = new ConcurrentHashMap<Integer, MessageComm>();

    private Map<Integer, Class<? extends AbstractNettyNetProtoBufMessage>> messages = new HashMap<Integer, Class<? extends AbstractNettyNetProtoBufMessage>>();

    private String namespace;

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
        loadPackage(namespace, ".class");
        loadPackage("com.twjitm.core.common.entity.online",".class");
    }

    public void loadPackage(String namespace, String suffix) {
        String[] fileNames = PackageScaner.scanNamespaceFiles(namespace, suffix, false, true);

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
            MessageCommandAnntation annotation = (MessageCommandAnntation) messageClass
                    .getAnnotation(MessageCommandAnntation.class);
            if (annotation != null && annotation.messagecmd() != null) {
                putMessages(annotation.messagecmd().commId, messageClass);
            }
        }

    }

    public MessageComm getMessageComm(int commId) {
        return messageCommMap.get(commId);
    }

    private void loadMessageCommId() {
        MessageComm[] comms = MessageComm.values();
        for (MessageComm comm : comms) {

        }
    }

}
