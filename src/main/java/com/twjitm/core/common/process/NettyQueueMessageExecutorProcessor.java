package com.twjitm.core.common.process;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.enums.MessageAttributeEnum;
import com.twjitm.core.common.factory.thread.ThreadNameFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.session.udp.NettyUdpSession;
import com.twjitm.core.common.utils.ExecutorUtil;
import com.twjitm.core.spring.SpringServiceManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author EGLS0807 - [Created on 2018-08-08 16:25]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 * 总消息处理
 */
public class NettyQueueMessageExecutorProcessor implements IMessageProcessor {
    private Logger logger = Logger.getLogger(NettyQueueMessageExecutorProcessor.class);
    /**
     * 消息队列 *
     */
    protected final BlockingQueue<AbstractNettyNetMessage> queue;

    /**
     * 消息处理线程停止时剩余的还未处理的消息
     **/
    private volatile List<AbstractNettyNetMessage> leftQueue;

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

    /**
     * 处理的消息总数
     */
    public long statisticsMessageCount = 0;

    private final boolean processLeft;

    public NettyQueueMessageExecutorProcessor() {
        this(true, 1);
    }

    public NettyQueueMessageExecutorProcessor(boolean processLeft, int executorCoreSize) {
        this.processLeft = processLeft;
        this.excecutorCoreSize = executorCoreSize;
        queue = new LinkedBlockingQueue<>();

    }

    @Override
    public void start() {
        if (this.executorService != null) {
            throw new IllegalStateException(
                    "The executorService has not been stopped.");
        }
        stop = false;
        ThreadNameFactory factory = new ThreadNameFactory(GlobalConstants.Thread.GAME_MESSAGE_QUEUE_EXCUTE);
        this.executorService = Executors.newFixedThreadPool(this.excecutorCoreSize, factory);

        for (int i = 0; i < this.excecutorCoreSize; i++) {
            this.executorService.execute(new Worker());
        }
        logger.info("NettyQueueMessageExecutorProcessor Message processor executorService started ["
                + this.executorService + " with " + this.excecutorCoreSize
                + " threads ]");
    }

    @Override
    public void stop() {
        logger.info("Message processor executor " + this + " stopping ...");
        this.stop = true;
        if (this.executorService != null) {
            //停止线程
            ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 50,
                    TimeUnit.MILLISECONDS);
            this.executorService = null;
        }
        logger.info("Message processor executor " + this + " stopped");
        if (this.processLeft) {
            // 将未处理的消息放入到leftQueue中,以备后续处理
            this.leftQueue = new LinkedList<AbstractNettyNetMessage>();
            while (true) {
                AbstractNettyNetMessage message = this.queue.poll();
                if (message != null) {
                    this.leftQueue.add(message);
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
            if (logger.isDebugEnabled()) {
                logger.debug("put queue size:" + queue.size());
            }
        } catch (InterruptedException e) {
            if (logger.isTraceEnabled()) {
                logger.error(e);
            }
        }
    }

    @Override
    public boolean isFull() {
        return this.queue.size() > 5000;
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                try {
                    process(queue.take());
                    if (logger.isDebugEnabled()) {
                        logger.debug("run queue size:" + queue.size());
                        logger.debug("run queue size:" + queue.size());
                    }
                } catch (InterruptedException e) {
                    if (logger.isTraceEnabled()) {
                        logger.warn("[#CORE.QueueMessageExecutorProcessor.run] [Stop process]");
                    }
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            }
            logger.debug("work is stop" + queue.size());
        }
    }

    private void process(AbstractNettyNetMessage msg) {
        if (msg == null) {
            if (logger.isTraceEnabled()) {
                logger.warn("[#CORE.QueueMessageExecutorProcessor.process] [" + "msg is null" + "]");
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
            NettyUdpSession clientSesion = (NettyUdpSession) abstractNetProtoBufMessage.getAttribute(MessageAttributeEnum.DISPATCH_SESSION);
            //所有的session已经强制绑定了，这里不需要再判定空了
            if (logger.isDebugEnabled()) {
                logger.debug("processor session" + clientSesion.getPlayerId() + " process message" + abstractNetProtoBufMessage.toString());
            }

            NettyNetMessageProcessLogic netMessageProcessLogic = SpringServiceManager.springLoadService.getNettyNetMessageProcessLogic();
            netMessageProcessLogic.processTcpMessage(msg, clientSesion);

        } catch (Exception e) {
            if (logger.isTraceEnabled()) {
                logger.error("Error#.QueueMessageExecutorProcessor.process");
                logger.error(e);
            }

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
