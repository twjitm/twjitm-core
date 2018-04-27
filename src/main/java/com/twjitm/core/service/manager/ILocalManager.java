package com.twjitm.core.service.manager;

/**
 * Created by нд╫╜ on 2018/4/27.
 */
public interface ILocalManager {
    public <T > T get(Class<T> clazz);

    public <X,Y extends X> void create(Class<Y> clazz,Class<X> inter) throws Exception;

    public <T> void add(Object service,Class<T> inter);
}
