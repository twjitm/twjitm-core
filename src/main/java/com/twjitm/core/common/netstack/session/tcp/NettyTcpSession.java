package com.twjitm.core.common.netstack.session.tcp;

import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.sender.NetTcpMessageSender;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.common.process.NetProtoMessageProcess;
import com.twjitm.core.common.update.IUpdatable;
import com.twjitm.core.spring.SpringServiceManager;
import io.netty.channel.Channel;

/**
 * @author twjitm - [Created on 2018-07-24 15:59]
 * @jdk java version "1.8.0_77"
 * tcp协议会话管理
 */
public class NettyTcpSession extends NettySession implements IUpdatable {
    /**
     * session id
     */
    private long sessionId;
    /**
     * 消息处理
     */
    private NetProtoMessageProcess netProtoMessageProcess;
    /**
     * 消息发送
     */
    private NetTcpMessageSender netTcpMessageSender;

    boolean netMessageProcessSwitch=true;


    public NettyTcpSession(Channel channel) {
        super(channel);
        sessionId = SpringServiceManager.springLoadManager.getLongIdGenerator().generateId();
        netProtoMessageProcess = new NetProtoMessageProcess(this);
        netTcpMessageSender = new NetTcpMessageSender(this);


    }
    /**
     * 增加消息处理切换。
     * @param switchFlag
     */
    public void processNetMessage(boolean switchFlag){
        if(netMessageProcessSwitch || switchFlag){
            netProtoMessageProcess.update();
        }
    }

    /**
     * 添加一个消息处理
     *
     * @param abstractNetMessage
     */
    public void addNetMessage(AbstractNettyNetMessage abstractNetMessage) {
        this.netProtoMessageProcess.addNetMessage(abstractNetMessage);
    }

    public void close() {

    }

    @Override
    public boolean update() {
        processNetMessage(false);
        return false;
    }
}
