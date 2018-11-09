package com.twjitm.core.common.handler.tcp;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;

/**
 * @author twjitm - [Created on 2018-08-15 18:20]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public class NettyNetLoggingHandler extends LoggingHandler {
    private Logger logger=Logger.getLogger(NettyNetLoggingHandler.class);

    public NettyNetLoggingHandler(LogLevel level) {
        super(level);
    }
    private final ChannelFutureListener exceptionListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if(!future.isSuccess()){
                Throwable throwable = future.cause();
                if(throwable != null) {
                    logger.error(throwable.toString(), throwable);
                }
            }
        }
    };
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.info(msg);
        }
        ChannelPromise unvoId = promise.unvoid();
        unvoId.addListener(exceptionListener);
        ctx.write(msg, promise);
    }
}
