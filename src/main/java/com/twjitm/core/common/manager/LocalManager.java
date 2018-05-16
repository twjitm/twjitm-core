package com.twjitm.core.common.manager;

/**
 * Created by 文江 on 2017/11/9.
 */


import com.twjitm.core.common.dispatcher.Dispatcher;
import com.twjitm.core.common.entity.online.OnlineUserPo;
import com.twjitm.core.common.factory.MessageRegistryFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 本地管理
 */
public class LocalManager implements ILocalManager {
    private static LocalManager localManager = new LocalManager();

    //缓存调用
    protected MessageRegistryFactory registryFactory;
    protected Dispatcher dispatcher;


    private Map<Class, Object> service;

    public LocalManager() {
        service = new LinkedHashMap<Class, Object>(40, 0.5f);
    }


    public static LocalManager getInstance() {
        return localManager;
    }


    public <X, Y extends X> void create(Class<Y> clazz, Class<X> inter) throws Exception {
        Object newObject = clazz.newInstance();
        /*if (newObject instanceof ILocalManager) {
            ((ILocalService) newObject).startup();
        }*/
        add(newObject, inter);
    }

    public void add(Object services, Class inter) {
        //接口和实现类必须相等或者继承关系
        if (!services.getClass().equals(inter) && !inter.isAssignableFrom(services.getClass())) {
            throw new IllegalArgumentException();
        }
        service.put(inter, services);
        if (services instanceof MessageRegistryFactory) {
            registryFactory = (MessageRegistryFactory) services;
        }
        if (services instanceof Dispatcher) {
            dispatcher = (Dispatcher) services;
        }

    }

    public <T> T get(Class<T> clzz) {
        return (T) service.get(clzz);
    }

    public OnlineUserPo getOnLineUser(int uId) {
        return null;
    }

    public MessageRegistryFactory getRegistryFactory() {
        return registryFactory;
    }

    public void setRegistryFactory(MessageRegistryFactory registryFactory) {
        this.registryFactory = registryFactory;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }


}
