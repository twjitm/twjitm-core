package com.twjitm.core.common.process.udp;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.process.IMessageProcessor;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

/**
 * @author twjitm - [Created on 2018-08-08 15:06]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 * udp协议消息处理的一种方式：生产者-----消费者模式
 */
public class NettyUdpNetProtoMessageProcessor implements IMessageProcessor {
    Logger logger = LoggerUtils.getLogger(NettyUdpNetProtoMessageProcessor.class);
    /**
     * 消息处理：服务器系统消息
     */
    private IMessageProcessor mainMessageProcessor;


    public NettyUdpNetProtoMessageProcessor(IMessageProcessor mainMessageProcessor) {
        this.mainMessageProcessor = mainMessageProcessor;
    }

    @Override
    public void startup() {
        this.mainMessageProcessor.startup();
    }

    @Override
    public void shutdown() {
        this.mainMessageProcessor.shutdown();
    }

    /**
     * 服务器消息选择处理，
     * 玩家消息直接处理
     *
     * @param msg
     */
    @Override
    public void put(AbstractNettyNetMessage msg) {
        if (!GlobalConstants.GameServiceRuntime.IS_OPEN) {
            return;
        }
        logger.info("放入消息执行队列"+msg.toString());
        this.mainMessageProcessor.put(msg);
    }

    @Override
    public boolean isFull() {
        return this.mainMessageProcessor.isFull();
    }
}
