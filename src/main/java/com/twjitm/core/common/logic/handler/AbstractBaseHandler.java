package com.twjitm.core.common.logic.handler;


import com.twjitm.core.common.annotation.MessageCommandAnnotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBaseHandler implements BaseHandler {

    public Map<Integer, Method> handlerMethods;

    public AbstractBaseHandler() {
        init();
    }

    public void init() {
        handlerMethods = new HashMap<>();
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MessageCommandAnnotation.class)) {
                MessageCommandAnnotation messageCommandAnnotation = method.getAnnotation(MessageCommandAnnotation.class);
                if (messageCommandAnnotation != null) {
                    handlerMethods.put(messageCommandAnnotation.messageCmd().commId, method);
                }
            }
        }

    }

    @Override
    public Method getMethod(int commId) {
        return handlerMethods.get(commId);
    }

}
