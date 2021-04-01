package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx10upload;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wongs.drunkard.base.constant.Constant;
import xyz.wongs.drunkard.base.utils.FileUtils;
import xyz.wongs.drunkard.base.utils.StringUtils;
import xyz.wongs.drunkard.base.utils.file.FileUtil;
import xyz.wongs.drunkard.common.enums.SysProperty;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx10upload.util.UploadUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ServerUploadHandler
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:02$
 * @Version 1.0.0
 */
public class ServerUploadHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger log = LoggerFactory.getLogger(ServerUploadHandler.class);

    private final static String UPLOAD_PATH= File.separator+"Doc"+File.separator+"upload";

    private HttpRequest request;

    /**
     * 解码器
     */
    private HttpPostRequestDecoder decoder;

    /**
     * Chunk
     */
    private boolean readingChunks;

    /**
     *  响应的消息体
     */
    private final StringBuilder responseContent = new StringBuilder();

    /**
     * 超过磁盘大小
     */
    private static final HttpDataFactory FACTORY = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    static {
        String path = System.getProperty(SysProperty.USER_DIR.getCode())+UPLOAD_PATH;
        FileUtils.checkDirectoryExit(path);
        DiskFileUpload.deleteOnExitTemporaryFile = true;
        DiskFileUpload.baseDirectory=path;

        DiskAttribute.deleteOnExitTemporaryFile=true;
        DiskAttribute.baseDirectory=path;
    }




    /** 不活动的Channel将被清理
     * @Description
     * @param ctx
     * @return void
     * @throws
     * @date 21/3/26 15:38
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("结束");
        writeFile();
        if (Optional.ofNullable(decoder).isPresent()) {
            decoder.cleanFiles();
        }
    }



    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 1、第一次交互需要将属性设置
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            if (!uri.getPath().startsWith("/form")) {
                // Write Menu
                UploadUtil.writeMenu(ctx,responseContent);
                return;
            }

            responseContent.setLength(0);
            responseContent.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
            responseContent.append("===================================\r\n");
            responseContent.append("VERSION: " + request.protocolVersion().text() + "\r\n");

            responseContent.append("REQUEST_URI: " + request.uri() + "\r\n\r\n");
            responseContent.append("\r\n\r\n");
            // new getMethod
            for (Map.Entry<CharSequence, CharSequence> entry : request.headers()) {
                responseContent.append("HEADER: " + entry.getKey() + '=' + entry.getValue() + "\r\n");
            }
            responseContent.append("\r\n\r\n");

            // new getMethod
            Set<Cookie> cookies;
            String value = request.headers().getAndConvert(HttpHeaderNames.COOKIE);
            if (value == null) {
                cookies = Collections.emptySet();
            } else {
                cookies = ServerCookieDecoder.decode(value);
            }
            for (Cookie cookie : cookies) {
                responseContent.append("COOKIE: " + cookie + "\r\n");
            }
            responseContent.append("\r\n\r\n");

            QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri());
            Map<String, List<String>> uriAttributes = decoderQuery.parameters();
            for (Map.Entry<String, List<String>> attr: uriAttributes.entrySet()) {
                for (String attrVal: attr.getValue()) {
                    responseContent.append("URI: " + attr.getKey() + '=' + attrVal + "\r\n");
                }
            }
            responseContent.append("\r\n\r\n");

            // if GET Method: should not try to create a HttpPostRequestDecoder
            if (request.method().equals(HttpMethod.GET)) {
                // GET Method: should not try to create a HttpPostRequestDecoder
                // So stop here
                responseContent.append("\r\n\r\nEND OF GET CONTENT\r\n");
                // Not now: LastHttpContent will be sent writeResponse(ctx.channel());
                return;
            }
            try {
                decoder = new HttpPostRequestDecoder(FACTORY, request);
            } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
                e1.printStackTrace();
                responseContent.append(e1.getMessage());
                UploadUtil.writeResponse(ctx.channel(),request,responseContent);
                ctx.channel().close();
                return;
            }

            readingChunks = HttpHeaderUtil.isTransferEncodingChunked(request);
            responseContent.append("Is Chunked: " + readingChunks + "\r\n");
            responseContent.append("IsMultipart: " + decoder.isMultipart() + "\r\n");
            if (readingChunks) {
                // Chunk version
                responseContent.append("Chunks: ");
                readingChunks = true;
            }
        }

        // check if the decoder was constructed before
        // if not it handles the form get
        if (decoder != null) {
            if (msg instanceof HttpContent) {
                // New chunk is received
                HttpContent chunk = (HttpContent) msg;
                try {
                    decoder.offer(chunk);
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
                    e1.printStackTrace();
                    responseContent.append(e1.getMessage());
                    UploadUtil.writeResponse(ctx.channel(),request,responseContent);
                    ctx.channel().close();
                    return;
                }
                responseContent.append('o');
                // example of reading chunk by chunk (minimize memory usage due to
                // Factory)
                UploadUtil.readHttpDataChunkByChunk(decoder,responseContent);
                // example of reading only if at the end
                if (chunk instanceof LastHttpContent) {
                    UploadUtil.writeResponse(ctx.channel(),request,responseContent);
                    readingChunks = false;
                    reset();
                }
            }
        } else {
            UploadUtil.writeResponse(ctx.channel(),request,responseContent);
        }
    }

    public void writeFile(){
        String path = System.getProperty(SysProperty.USER_DIR.getCode())+UPLOAD_PATH+File.separator+"temp";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            file = new File(path+File.separator+ StringUtils.getUuid()+Constant.POINT+"log");
            file.createNewFile();
            FileUtil.writeString(responseContent.toString(),file, Constant.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(responseContent.toString(), cause);
        ctx.channel().close();
    }


    private void reset() {
        request = null;
        // destroy the decoder to release all resources
        decoder.destroy();
        decoder = null;
    }

}
