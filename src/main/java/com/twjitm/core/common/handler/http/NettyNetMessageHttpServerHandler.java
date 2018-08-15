package com.twjitm.core.common.handler.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map;

/**
 * @author EGLS0807 - [Created on 2018-08-14 17:09]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 * http  server handler
 */
public class NettyNetMessageHttpServerHandler extends AbstractNettyNetMessageHttpServerHandler {
    private HttpRequest request;
    private StringBuilder log=new StringBuilder();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

      /*  if (msg instanceof HttpRequest) {
            request= (HttpRequest) msg;
            boolean isKeepAlive = HttpUtil.isKeepAlive(request);
           if(request.getMethod()!=HttpMethod.POST){
               throw new IllegalStateException("request not is get method.");
           }
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
            if (gameServerConfig.isDevelopModel()) {
                if (buf.isReadable()) {
                    log.append("CONTENT: ");
                    log.append(buf.toString(CharsetUtil.UTF_8));
                    log.append("\r\n");
                    appendDecoderResult(log, request);
                }
            }
        }*/
    }
}
