package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx09download;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx09download.util.HandlerUtil;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx11heartbeat.ClientHeartBeatHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ServerDownloadHandler
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:02$
 * @Version 1.0.0
 */
public class ServerDownloadHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger log = LoggerFactory.getLogger(ServerDownloadHandler.class);

    private final String url;


    public ServerDownloadHandler(String url){
        this.url=url;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        HandlerUtil.checkRequest(ctx,request);
        final String uri = request.uri();
        final String path = HandlerUtil.resolveUri(url,uri);
        HandlerUtil.checkFile(ctx,path,uri);
        File file = new File(path);
        //随机文件读写类
        RandomAccessFile randomAccessFile = null;
        try {
            // 以只读的方式打开文件
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException fnfe) {
            // 404
            HandlerUtil.sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }

        //获取文件长度
        long fileLength = randomAccessFile.length();
        //建立响应对象
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        //设置响应信息
        HttpHeaderUtil.setContentLength(response, fileLength);
        //设置响应头
        HandlerUtil.setContentTypeHeader(response, file);
        //如果一直保持连接则设置响应头信息为：HttpHeaders.Values.KEEP_ALIVE
        if (HttpHeaderUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        //进行写出
        ctx.write(response);

        //构造发送文件线程，将文件写入到Chunked缓冲区中
        ChannelFuture sendFileFuture;
        //写出ChunkedFile
        sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
        //添加传输监听
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                if (total < 0) { // total unknown
                    log.warn("Transfer progress: " + progress);
                } else {
                    log.warn("Transfer progress: " + progress + " / " + total);
                }
            }
            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                log.warn("Transfer complete.");
            }
        });

        //如果使用Chunked编码，最后则需要发送一个编码结束的看空消息体，进行标记，表示所有消息体已经成功发送完成。
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        //如果当前连接请求非Keep-Alive ，最后一包消息发送完成后 服务器主动关闭连接
        if (!HttpHeaderUtil.isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(ctx.channel().isActive()){
            HandlerUtil.sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
            ctx.close();
        }
    }
}
