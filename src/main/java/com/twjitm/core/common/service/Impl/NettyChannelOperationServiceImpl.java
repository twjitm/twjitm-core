package com.twjitm.core.common.service.Impl;

import com.twjitm.core.common.netstack.session.ISession;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.common.netstack.session.tcp.NettyTcpSession;
import com.twjitm.core.common.service.INettyChannleOperationService;
import com.twjitm.core.common.service.IService;
import com.twjitm.core.utils.logs.LoggerUtils;
import com.twjitm.core.utils.uuid.AtomicLimitNumber;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author twjitm - [Created on 2018-07-26 20:41]
 * netty channle 操作服务类
 * @jdk java version "1.8.0_77"
 */
@Service
public class NettyChannelOperationServiceImpl implements INettyChannleOperationService, IService {
    protected static final Logger logger = LoggerUtils.getLogger(NettyChannelOperationServiceImpl.class);

    protected ConcurrentHashMap<Long, NettySession> sessions = new ConcurrentHashMap<Long, NettySession>();

    private AtomicLimitNumber atomicLimitNumber;

    @Override
    public ISession findNettySession(long sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public boolean addNettySession(NettyTcpSession session) {
        long current = atomicLimitNumber.increment();
        if (!checkMaxNumber(current)) {
            atomicLimitNumber.decrement();
            return false;
        }
        sessions.put(session.getSessionId(), session);
        return true;
    }

    /**
     * 是否超过tcp最大连接数
     *
     * @param current
     * @return
     */
    private boolean checkMaxNumber(long current) {
        return current < 1000;
    }

    @Override
    public void removeNettySession(long session) {
        atomicLimitNumber.decrement();
        sessions.remove(session);
    }

    @Override
    public String getId() {
        return this.getClass().getName();
    }

    @Override
    public void startup() throws Exception {
        atomicLimitNumber = new AtomicLimitNumber();
    }

    @Override
    public void shutdown() throws Exception {
       sessions.clear();
    }

    public ConcurrentHashMap<Long, NettySession> getSessions() {
        return sessions;
    }
}
