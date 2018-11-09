package com.twjitm.core.common.process.tcp;

import com.twjitm.core.common.config.global.GlobalConstants;
import com.twjitm.core.common.enums.MessageAttributeEnum;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.common.process.NettyNetMessageProcessLogic;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import com.twjitm.threads.thread.NettyThreadNameFactory;
import com.twjitm.threads.utils.ExecutorUtil;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author twjitm - [Created on 2018-08-06 20:43]
 * @company https://github.com/twjitm/
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
    private int executorCoreSize;

    /**
     * 是否停止
     */
    private volatile boolean stop = false;
    private volatile boolean inLastQueue = false;
    private int statisticsMessageCount = 0;

    public NettyTcpMessageQueueExecutorProcessor() {
        this(false, 1);
    }

    public NettyTcpMessageQueueExecutorProcessor(boolean inLastQueue, int executorCoreSize) {
        this.executorCoreSize = executorCoreSize;
        this.inLastQueue = inLastQueue;
        queue = new LinkedBlockingQueue<>(executorCoreSize);
    }

    @Override
    public void putDirectTcpMessage() {

    }

    @Override
    public void startup() {
        if (this.executorService != null) {
            throw new IllegalStateException(
                    "执行器没有被停止");
        }
        stop = false;
        NettyThreadNameFactory factory = new NettyThreadNameFactory(GlobalConstants.Thread.MESSAGE_QUEUE_EXECUTOR);
        this.executorService = Executors
                .newFixedThreadPool(this.executorCoreSize, factory);
        for (int i = 0; i < this.executorCoreSize; i++) {
            this.executorService.execute(new Processor());
        }
        logger.info("NETTYTCPMESSAGEQUEUEEXECUTORPROCESSOR  MESSAGE HANDLER EXECUTORSERVICE BEGIN ["
                + this.executorService + " WITH " + this.executorCoreSize
                + " THREADS ]");

    }

    @Override
    public void shutdown() {
        logger.info("MESSAGE STOPPING " + this);
        this.stop = true;
        if (this.executorService != null) {
            ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 50,
                    TimeUnit.MILLISECONDS);
            this.executorService = null;
        }
        logger.info("MESSAGE HANDLER STOP EXECUTOR IS OVER" + this);
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
            logger.error("MESSAGE EXECUTION THREAD INTERRUPTED..",e.getCause());
        } finally {
            logger.info("MESSAGE SUCCESSFUL PUT QUEUE.");
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
                        logger.debug("RUN QUEUE SIZE:" + queue.size());
                    }
                } catch (InterruptedException e) {
                    if (logger.isTraceEnabled()) {
                        logger
                                .warn("[#CORE.QUEUEMESSAGEEXECUTORPROCESSOR.RUN] [STOP PROCESS]");
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
                logger.warn("[#CORE.QUEUEMESSAGEEXECUTORPROCESSOR.PROCESS]");
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
                logger.debug("PROCESSOR SESSION" + clientSession.getPlayerId() + " PROCESS MESSAGE" + abstractNetProtoBufMessage.toString());
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
                    logger.info("#CORE.MSG.PROCESS.DISPATCH_STATICS DISPTACH MESSAGE ID:"
                            + msg.getNetMessageHead().getCmd() + " TIME:"
                            + time + "MS" + " TOTAL:"
                            + this.statisticsMessageCount);
                }
            }
        }
    }


}
