package com.twjitm.core.common.service;

/**
 * @author twjitm - [Created on 2018-08-08 14:01]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public interface INettyLongFindService<T extends ILongId> {
    /**
            * 查找
     * @param id
     * @return
             */
    public T findT(long id);

    /**
     * 增加
     * @param t
     */
    public void addT(T t);

    /**
     * 移除
     * @param t
     * @return
     */
    public T removeT(T t);

    /**
     * 清除所有
     */
    public void clear();
}
