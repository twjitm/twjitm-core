package com.twjitm.core.common.process;

import com.twjitm.core.common.factory.INettyTcpMessageFactory;
import com.twjitm.core.common.netstack.coder.encode.http.INettyNetProtoBufHttpMessageEncoderFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.netstack.session.NettySession;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import com.twjitm.core.utils.time.TimeUtils;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author twjitm - [Created on 2018-07-24 20:19]
 * @jdk java version "1.8.0_77"
 * <p>
 * real message handler process logic
 */
public class NettyNetMessageProcessLogic {
    Logger logger = LoggerUtils.getLogger(this.getClass());

    /**
     * handler tcp message
     *
     * @param message
     * @param session
     */
    public void processTcpMessage(AbstractNettyNetMessage message, NettySession session) {
        /**
         * 返回最准确的可用系统计时器的当前值，以毫微秒为单位
         */
        long begin = System.nanoTime();
        IDispatcherService dispatcherService = SpringServiceManager.springLoadService.getDispatcherService();
        AbstractNettyNetProtoBufMessage response = dispatcherService.dispatcher(message);
        if (response != null) {
            response.getNetMessageHead().setSerial(message.getNettyNetMessageHead().getSerial());
            try {
                session.write(response);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }
        }
        long end = System.nanoTime();
       double difference=(end-begin)/1000000000;
       logger.info("HANDLER MESSAGE CONSUME TIME "+difference+" s(秒)");
        if (logger.isInfoEnabled()) {
            logger.info("HANDLER MESSAGE CONSUME TIME=" + (end - begin));
        }
    }

    /**
     * handler message by http
     */
    public HttpResponse processHttpMessage(AbstractNettyNetMessage message, HttpRequest request) {
        FullHttpResponse httpResponse = null;
        AbstractNettyNetProtoBufMessage respone = null;
        long begin = System.nanoTime();
        try {
            IDispatcherService dispatcherService = SpringServiceManager.getSpringLoadService().getDispatcherService();
            respone = dispatcherService.dispatcher(message);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.error("ERROR#.QUEUEMESSAGEEXECUTORPROCESSOR.PROCESS");
            }
            if (logger.isInfoEnabled()) {
                // 特例，统计时间跨度
                long time = (System.nanoTime() - begin) / (1000 * 1000);
                if (time > 1) {
                    logger.info("#CORE.MSG.PROCESS.STATICS Message id:"
                            + message.getNetMessageHead().getCmd() + " Time:"
                            + time + "ms");
                }
            }
        }
        INettyNetProtoBufHttpMessageEncoderFactory netProtoBufHttpMessageEncoderFactory = SpringServiceManager.getSpringLoadService().getNettyNetProtoBufHttpMessageEncoderFactory();
        if (respone != null) {
            respone.setSerial(message.getNetMessageHead().getSerial());
            try {
                httpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer(netProtoBufHttpMessageEncoderFactory.createByteBuf(respone)));
                logger.info("HANDLER HTTP MESSAGE SUCCESSFUL");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return httpResponse;
    }


}
