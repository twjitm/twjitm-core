package com.twjitm.core.common.process.tcp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.common.update.IUpdatable;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author twjitm - [Created on 2018-07-24 15:49]
 * @jdk java version "1.8.0_77"
 * 消息处理
 */

public class NettyTcpNetProtoMessageProcess implements INettyTcpNetProtoMessageProcess, IUpdatable {
    protected Logger logger = LoggerUtils.getLogger(NettyTcpNetProtoMessageProcess.class);
    private long processMessageNumber;
    private NettySession nettySession;
    /**
     * 消息队列
     */
    private Queue<AbstractNettyNetProtoBufMessage> nettyNetMessageQueue;

    /**
     *
     */
    protected boolean suspendedProcess=true;

    public NettyTcpNetProtoMessageProcess() {

    }

    public NettyTcpNetProtoMessageProcess(NettySession nettySession) {
        this.nettySession = nettySession;
        if( this.nettyNetMessageQueue==null){
            this.nettyNetMessageQueue = new ConcurrentLinkedDeque<AbstractNettyNetProtoBufMessage>();
        }
    }

    /**
     *TODO maybe have bug
     */
    @Override
    public void processNetMessage() {
        nettySession.getPlayerId();
        AbstractNettyNetProtoBufMessage message = nettyNetMessageQueue.poll();
        while (isSuspendedProcess() && message != null) {
            processMessageNumber++;
            NettyTcpMessageQueueExecutorProcessor logic = SpringServiceManager.springLoadService.getNettyTcpMessageQueueExecutorProcessor();
            logic.put(message);
            message = nettyNetMessageQueue.poll();
        }
       logger.info("HANDLER OVER MESSAGE NUMBER="+processMessageNumber);
    }

    @Override
    public void addNetMessage(AbstractNettyNetMessage abstractNettyNetMessage) {
        nettyNetMessageQueue.add((AbstractNettyNetProtoBufMessage) abstractNettyNetMessage);
    }

    @Override
    public void close() {
        nettyNetMessageQueue.clear();
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
