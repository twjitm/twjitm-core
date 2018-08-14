package com.twjitm.core.common.process.tcp;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.enums.MessageAttributeEnum;
import com.twjitm.core.common.factory.thread.ThreadNameFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.common.process.NettyNetMessageProcessLogic;
import com.twjitm.core.common.utils.ExecutorUtil;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author EGLS0807 - [Created on 2018-08-06 20:43]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class NettyTcpMessageQueueExecutorProcessor implements ITcpMessageProcessor {
    public static final Logger logger = LoggerUtils.getLogger(NettyTcpMessageQueueExecutorProcessor.class);
    /**
     * 消息队列 *
     */
    protected final BlockingQueue<AbstractNettyNetMessage> queue;

    /**
     * 消息处理线程停止时剩余的还未处理的消息
     **/
    private volatile List<AbstractNettyNetMessage> lastQueue;

    /**
     * 消息处理线程池
     */
    private volatile ExecutorService executorService;

    /**
     * 线程池的线程个数
     */
    private int excecutorCoreSize;

    /**
     * 是否停止
     */
    private volatile boolean stop = false;
    private volatile boolean inLastQueue = false;
    private int statisticsMessageCount = 0;

    public NettyTcpMessageQueueExecutorProcessor() {
        this(false, 1);
    }

    public NettyTcpMessageQueueExecutorProcessor(boolean inLastQueue, int excecutorCoreSize) {
        this.excecutorCoreSize = excecutorCoreSize;
        this.inLastQueue = inLastQueue;
        queue = new LinkedBlockingQueue<>(excecutorCoreSize);
    }

    @Override
    public void putDirectTcpMessage() {

    }

    @Override
    public void start() {
        if (this.executorService != null) {
            throw new IllegalStateException(
                    "执行器没有被停止");
        }
        stop = false;
        ThreadNameFactory factory = new ThreadNameFactory(GlobalConstants.Thread.MESSAGE_QUEUE_EXECUTOR);
        this.executorService = Executors
                .newFixedThreadPool(this.excecutorCoreSize, factory);
        for (int i = 0; i < this.excecutorCoreSize; i++) {
            this.executorService.execute(new Processor());
        }
        logger.info("NettyTcpMessageQueueExecutorProcessor  message handler executorService begin ["
                + this.executorService + " with " + this.excecutorCoreSize
                + " threads ]");

    }

    @Override
    public void stop() {
        logger.info("消息处理器开始停止： " + this);
        this.stop = true;
        if (this.executorService != null) {
            ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 50,
                    TimeUnit.MILLISECONDS);
            this.executorService = null;
        }
        logger.info("消息处理执行器处理完毕" + this);
        if (this.inLastQueue) {
            // 将未处理的消息放入到leftQueue中,以备后续处理
            this.lastQueue = new LinkedList<AbstractNettyNetMessage>();
            while (true) {
                AbstractNettyNetMessage _msg = this.queue.poll();
                if (_msg != null) {
                    this.lastQueue.add(_msg);
                } else {
                    break;
                }
            }
        }
        this.queue.clear();

    }

    @Override
    public void put(AbstractNettyNetMessage msg) {
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(msg);
        } finally {
            logger.info("消息执行线程被中断。。。。");
        }
    }

    @Override
    public boolean isFull() {
        return false;
    }

    private class Processor implements Runnable {

        @Override
        public void run() {
            while (!stop) {
                try {
                    process(queue.take());
                    if (logger.isDebugEnabled()) {
                        logger.debug("run queue size:" + queue.size());
                    }
                } catch (InterruptedException e) {
                    if (logger.isTraceEnabled()) {
                        logger
                                .warn("[#CORE.QueueMessageExecutorProcessor.run] [Stop process]");
                    }
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            }
        }
    }

    public void process(AbstractNettyNetMessage msg) {
        if (msg == null) {
            if (logger.isTraceEnabled()) {
                logger.warn("[#CORE.QueueMessageExecutorProcessor.process]");
            }
            return;
        }
        long begin = 0;
        if (logger.isInfoEnabled()) {
            begin = System.nanoTime();
        }
        this.statisticsMessageCount++;
        try {
            AbstractNettyNetProtoBufMessage abstractNetProtoBufMessage = (AbstractNettyNetProtoBufMessage) msg;
            NettySession clientSession = (NettySession) abstractNetProtoBufMessage.getAttribute(MessageAttributeEnum.DISPATCH_SESSION);
            if (logger.isDebugEnabled()) {
                logger.debug("processor session" + clientSession.getPlayerId() + " process message" + abstractNetProtoBufMessage.toString());
            }

            NettyNetMessageProcessLogic netMessageProcessLogic = SpringServiceManager.springLoadService.getNettyNetMessageProcessLogic();
            netMessageProcessLogic.processTcpMessage(msg, clientSession);

        } catch (Exception e) {
            logger.error(e);

        } finally {
            if (logger.isInfoEnabled()) {
                // 特例，统计时间跨度
                long time = (System.nanoTime() - begin) / (1000 * 1000);
                if (time > 1) {
                    logger.info("#CORE.MSG.PROCESS.DISPATCH_STATICS disptach Message id:"
                            + msg.getNetMessageHead().getCmd() + " Time:"
                            + time + "ms" + " Total:"
                            + this.statisticsMessageCount);
                }
            }
        }
    }


}
