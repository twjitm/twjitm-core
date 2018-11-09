package com.twjitm.core.common.handler.http;

import com.twjitm.core.common.enums.MessageAttributeEnum;
import com.twjitm.core.common.netstack.coder.decode.http.INettyNetProtoBuffHttpToMessageDecoderFactory;
import com.twjitm.core.common.netstack.coder.decode.http.NettyNetProtoBuffHttpToMessageDecoderFactory;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.common.process.NettyNetMessageProcessLogic;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author twjitm - [Created on 2018-08-14 17:09]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 * http  server handler
 */
public class NettyNetMessageHttpServerHandler extends AbstractNettyNetMessageHttpServerHandler {
    private HttpRequest request;
    private StringBuilder log = new StringBuilder();
    private Logger logger=LoggerUtils.getLogger(NettyNetMessageHttpServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            boolean isKeepAlive = HttpUtil.isKeepAlive(request);
            if (request.getMethod() != HttpMethod.POST) {
                throw new IllegalStateException("request not is get method.");
            }
            /**
             *
             *100 Continue
             * 是这样的一种情况：HTTP客户端程序有一个实体的主体部分要发送给服务器，但希望在发送之前查看下服务器是否会
             * 接受这个实体，所以在发送实体之前先发送了一个携带100
             * Continue的Expect请求首部的请求。服务器在收到这样的请求后，应该用 100 Continue或一条错误码来进行响应。
             *
             */
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
            log.setLength(0);
            log.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
            log.append("===================================\r\n");

            log.append("VERSION: ").append(request.protocolVersion()).append("\r\n");
            log.append("HOSTNAME: ").append(request.headers().get(HttpHeaderNames.HOST, "unknown")).append("\r\n");
            log.append("REQUEST_URI: ").append(request.uri()).append("\r\n\r\n");
            HttpHeaders headers = request.headers();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> h : headers) {
                    CharSequence key = h.getKey();
                    CharSequence value = h.getValue();
                    log.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
                }
                log.append("\r\n");
            }
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
            Map<String, List<String>> params = queryStringDecoder.parameters();
            if (!params.isEmpty()) {
                for (Map.Entry<String, List<String>> p : params.entrySet()) {
                    String key = p.getKey();
                    List<String> vals = p.getValue();
                    for (String val : vals) {
                        log.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
                    }
                }
                log.append("\r\n");
                appendDecoderResult(log, request);
            }
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            // if (gameServerConfig.isDevelopModel()) {
            if (buf.isReadable()) {
                log.append("CONTENT: ");
                log.append(buf.toString(CharsetUtil.UTF_8));
                log.append("\r\n");
                appendDecoderResult(log, request);
            }
            //  }

            //开始解析
            INettyNetProtoBuffHttpToMessageDecoderFactory netProtoBufHttpMessageDecoderFactory = SpringServiceManager.getSpringLoadService().getNettyNetProtoBuffHttpToMessageDecoderFactory();
            AbstractNettyNetProtoBufMessage nettyNetProtoBufMessage = netProtoBufHttpMessageDecoderFactory.parseMessage(buf);
            //封装属性
            nettyNetProtoBufMessage.setAttribute(MessageAttributeEnum.DISPATCH_HTTP_REQUEST, request);
            //直接进行处理
            NettyNetMessageProcessLogic netMessageProcessLogic = SpringServiceManager.getSpringLoadService().getNettyNetMessageProcessLogic();
            HttpResponse httpResponse = netMessageProcessLogic.processHttpMessage(nettyNetProtoBufMessage, request);
            writeResponse(httpResponse, ctx);

        }
    }

    private void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
        ctx.write(response);
    }

    private static void appendDecoderResult(StringBuilder buf, HttpObject o) {
        DecoderResult result = o.decoderResult();
        if (result.isSuccess()) {
            return;
        }
        buf.append(". WITH DECODER FAILURE: ");
        buf.append(result.cause());
        buf.append("\r\n");
    }

    private boolean writeResponse(HttpResponse response, ChannelHandlerContext ctx) {
        // Decide whether to close the connection or not.
        if(response==null){
            return false;
        }
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        FullHttpResponse fullHttpResponse = (FullHttpResponse) response;
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
        if (keepAlive) {
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        // Encode the cookie.
        String cookieString = request.headers().get(HttpHeaderNames.COOKIE);
        if (cookieString != null) {
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieString);
            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                for (io.netty.handler.codec.http.cookie.Cookie cookie : cookies) {
                    response.headers().add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.STRICT.encode(cookie));
                }
            }
        } else {
            // Browser sent no cookie.  Add some.
            response.headers().add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.STRICT.encode("key1", "value1"));
            response.headers().add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.STRICT.encode("key2", "value2"));
        }
        if (!keepAlive) {
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.writeAndFlush(response);
        }
        return keepAlive;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.info(cause.getCause());

    }
}
