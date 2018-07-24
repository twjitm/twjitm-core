package com.twjitm.core.common.process;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.common.update.IUpdatable;
import com.twjitm.core.spring.SpringLoadManager;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author EGLS0807 - [Created on 2018-07-24 15:49]
 * @jdk java version "1.8.0_77"
 * 消息处理器
 */
public class NetProtoMessageProcess implements INetProtoMessageProcess, IUpdatable {
    protected Logger logger = LoggerUtils.getLogger(NetProtoMessageProcess.class);
    private int processMessageNumber;
    private NettySession nettySession;
    /**
     * 消息处理队列
     */
    private Queue<AbstractNettyNetProtoBufMessage> netMessagequeue;

    /**
     * 中断消息处理
     */
    protected boolean suspendedProcess;

    public NetProtoMessageProcess(NettySession nettySession) {
        this.nettySession = nettySession;
        netMessagequeue = new ConcurrentLinkedDeque<AbstractNettyNetProtoBufMessage>();
    }

    /**
     * 消息处理
     */
    @Override
    public void processNetMessage() {
        nettySession.getPlayerId();
        AbstractNettyNetProtoBufMessage message = netMessagequeue.poll();
        while (isSuspendedProcess() && message != null) {
            processMessageNumber++;
            NettyNetMessageProcessLogic logic = SpringServiceManager.springLoadManager.getNettyNetMessageProcessLogic();
            logic.processTcpMessage(message, nettySession);
            message = netMessagequeue.poll();
        }

    }

    @Override
    public void addNetMessage(AbstractNettyNetMessage abstractNettyNetMessage) {
        netMessagequeue.add((AbstractNettyNetProtoBufMessage) abstractNettyNetMessage);
    }

    @Override
    public void close() {
        netMessagequeue.clear();
        setSuspendedProcess(true);
    }

    @Override
    public boolean update() {
        processNetMessage();
        return false;
    }

    public boolean isSuspendedProcess() {
        return suspendedProcess;
    }

    public void setSuspendedProcess(boolean suspendedProcess) {
        this.suspendedProcess = suspendedProcess;
    }
}
