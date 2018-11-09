package com.twjitm.core.common.service.Impl;

import com.twjitm.core.common.service.ILongId;
import com.twjitm.core.common.service.INettyLongFindService;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 维护本地在线玩家集和服务
 *
 * @author twjitm - [Created on 2018-08-08 14:06]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public abstract class AbstractNettyGamePlayerFindService<T extends ILongId> implements INettyLongFindService<T> {
    private Logger logger = LoggerUtils.getLogger(AbstractNettyGamePlayerFindService.class);
    private volatile ConcurrentHashMap<Long, T> concurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public T findT(long id) {
        if (logger.isTraceEnabled()) {
            logger.info("NETTY GAME FIND ID =" + id);
        }
        return concurrentHashMap.get(id);
    }

    @Override
    public void addT(T t) {
        if (logger.isTraceEnabled()) {
            logger.info("NETTY GAME ADD ID =" + t.getClass().getSimpleName() + "ID IS" + t.getLongId());
        }
        concurrentHashMap.put(t.getLongId(), t);
    }

    @Override
    public T removeT(T t) {
        if (logger.isTraceEnabled()) {
            logger.info("NETTY GAME REMOVE ID =" + t.getClass().getSimpleName() + "ID IS" + t.getLongId());
        }
        return concurrentHashMap.remove(t.getLongId());
    }

    @Override
    public void clear() {
        if (logger.isTraceEnabled()) {
            logger.info("NETTY GAME CLEAR ALL");
        }
        concurrentHashMap.clear();
    }
}
