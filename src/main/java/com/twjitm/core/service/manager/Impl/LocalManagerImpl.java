package com.twjitm.core.service.manager.Impl;

import com.twjitm.core.service.manager.ILocalManager;
import org.springframework.stereotype.Service;

/**
 * Created by twjitm on 2018/4/27.
 */
@Service
public class LocalManagerImpl implements ILocalManager {

    public <T> T get(Class<T> clazz) {
        return null;
    }

    public <X, Y extends X> void create(Class<Y> clazz, Class<X> inter) throws Exception {
        Object newObject = clazz.newInstance();
        /*if (newObject instanceof ILocalManager) {
            ((ILocalService) newObject).startup();
        }*/
        add(newObject, inter);
    }

    public <T> void add(Object service, Class<T> inter) {
        try {
            create((Class<T>) service,inter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
