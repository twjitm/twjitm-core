package com.twjitm.core.common.factory.classload;

/**
 * Created by 文江 on 2017/11/9.
 * 扩展类加载器，加载class文件
 */
public class NettyClassloader extends ClassLoader {

    public Class<?> findclss(byte[] b) {
        return defineClass(null, b, 0, b.length);
    }
}
