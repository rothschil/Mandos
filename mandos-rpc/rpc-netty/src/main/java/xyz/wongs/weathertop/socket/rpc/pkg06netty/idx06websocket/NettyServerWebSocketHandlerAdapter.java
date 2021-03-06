package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx06websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ChannelHandlerAdapter$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:02$
 * @Version 1.0.0
 */
public class NettyServerWebSocketHandlerAdapter extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = Logger.getLogger(NettyServerWebSocketHandlerAdapter.class.getName());

    private WebSocketServerHandshaker handshaker;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object object)
            throws Exception {
        if(object instanceof FullHttpRequest){
            handleHttpRequest(ctx,(FullHttpRequest)object);
        }

        if(object instanceof WebSocketFrame){
            handleWebSocketFrame(ctx,(WebSocketFrame)object);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest request){
        if(!request.decoderResult().isSuccess() || ! ("websocket".equals(request.headers().get("Upgrade").toString()))){
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // ???????????????????????????????????????
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
        handshaker = wsFactory.newHandshaker(request);

        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), request);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        // ????????????????????????????????????
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // ???????????????Ping??????
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // ?????????????????????????????????????????????????????????
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }

        // ??????????????????
        String request = ((TextWebSocketFrame) frame).text();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("%s received %s", ctx.channel(), request));
        }
        ctx.channel().write(
                new TextWebSocketFrame(request + " , ????????????Netty WebSocket????????????????????????" + new java.util.Date().toString()));
    }


    private static void sendHttpResponse(ChannelHandlerContext ctx,FullHttpRequest request, FullHttpResponse response){
        // ????????????????????????
        if (response.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            HttpHeaderUtil.setContentLength(request, response.content().readableBytes());
        }

        // ????????????Keep-Alive???????????????
        ChannelFuture f = ctx.channel().writeAndFlush(request);
        if (!HttpHeaderUtil.isKeepAlive(request) || response.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
