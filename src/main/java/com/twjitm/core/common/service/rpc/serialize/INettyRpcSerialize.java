package com.twjitm.core.common.service.rpc.serialize;

/**
 * Created by IntelliJ IDEA.
 * User: 文江 Date: 2018/8/19  Time: 9:21
 * https://blog.csdn.net/baidu_23086307
 */
public interface INettyRpcSerialize {
     <T> byte[] serialize(T obj);

     <T> T deserialize(byte[] data, Class<T> cls);
}
