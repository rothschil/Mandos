package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx09download.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import xyz.wongs.drunkard.base.constant.Constant;
import xyz.wongs.drunkard.common.enums.SysProperty;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

/**
 * @author WCNGS@QQ.COM
 * @ClassName HandlerUtil$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/26$ 09:45$
 * @Version 1.0.0
 */
public class HandlerUtil {

    /**
     * 非法URI正则
     */
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    /**
     * 文件是否被允许访问下载验证
     */
    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");


    /** 解析URI
     * @Description
     * @param uri
     * @return java.lang.String 解析的结果
     * @throws
     * @date 21/3/24 16:01
     */
    public static String resolveUri(String url,String uri){
        try {
            //使用UTF-8字符集
            uri = URLDecoder.decode(uri, Constant.UTF8);
        } catch (UnsupportedEncodingException e) {
            try {
                //尝试ISO-8859-1
                uri = URLDecoder.decode(uri, Constant.ISO_8859);
            } catch (UnsupportedEncodingException e1) {
                //抛出预想外异常信息
                throw new Error();
            }
        }
        // 对uri进行细粒度判断：4步验证操作
        // step 1 基础验证
        if (!uri.startsWith(url)) {
            return null;
        }
        // step 2 基础验证
        if (!uri.startsWith("/")) {
            return null;
        }
        // step 3 将文件分隔符替换为本地操作系统的文件路径分隔符
        uri = uri.replace('/', File.separatorChar);
        // step 4 二次验证合法性
        if (uri.contains(File.separator + '.')
                || uri.contains('.' + File.separator) || uri.startsWith(".")
                || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }
        //当前工程所在目录 + URI构造绝对路径进行返回
        return System.getProperty(SysProperty.USER_DIR.getCode()) + File.separator + uri;
    }

    /** 错误信息
     * @Description
     * @param ctx
     * @param status 状态
     * @return void
     * @throws
     * @date 21/3/24 15:51
     */
    public static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        //建立响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString()+ "\r\n", CharsetUtil.UTF_8));
        //设置响应头信息
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        //使用ctx对象写出并且刷新到SocketChannel中去 并主动关闭连接(这里是指关闭处理发送数据的线程连接)
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    public static void setContentTypeHeader(HttpResponse response, File file) {
        //使用mime对象获取文件类型
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType(file.getPath()));
    }


    /** 检查文件路径
     * @Description
     * @param path
     * @return void
     * @throws
     * @date 21/3/24 15:58
     */
    public static void checkFile(ChannelHandlerContext ctx,String path,String uri){
        if (path == null) {
            HandlerUtil.sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        // 创建file对象
        File file = new File(path);
        // 判断文件是否为隐藏或者不存在
        if (file.isHidden() || !file.exists()) {
            // 404
            HandlerUtil.sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }

        if(file.isDirectory()){
            if(uri.endsWith("/")){
                // 如果以正常"/"结束 说明是访问的一个文件目录：则进行展示文件列表
                // （web服务端则可以跳转一个Controller，遍历文件并跳转到一个页面）
                sendList(ctx, file);
            } else {
                //如果非"/"结束 则重定向，补全"/" 再次请求
                sendRedirect(ctx, uri + '/');
            }
        }

        // 如果所创建的file对象不是文件类型
        if (!file.isFile()) {
            // 403
            HandlerUtil.sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
    }

    /** 重定向
     * @Description
     * @param ctx
     * @param newUri 跳转的地址
     * @return void
     * @throws
     * @date 21/3/24 15:51
     */
    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        //建立响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        //设置新的请求地址放入响应对象中去
        response.headers().set(HttpHeaderNames.LOCATION, newUri);
        //使用ctx对象写出并且刷新到SocketChannel中去 并主动关闭连接(这里是指关闭处理发送数据的线程连接)
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


    public static void sendList(ChannelHandlerContext ctx, File dir){
        // 设置响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        // 响应头
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        // 追加文本内容
        StringBuilder ret = new StringBuilder();
        String dirPath = dir.getPath();
        ret.append("<!DOCTYPE html>\r\n");
        ret.append("<html><head><title>");
        ret.append(dirPath);
        ret.append(" 目录：");
        ret.append("</title></head><body>\r\n");
        ret.append("<h3>");
        ret.append(dirPath).append(" 目录：");
        ret.append("</h3>\r\n");
        ret.append("<ul>");
        ret.append("<li>链接：<a href=\"../\">..</a></li>\r\n");

        // 遍历文件 添加超链接
        for (File f : dir.listFiles()) {
            //step 1: 跳过隐藏或不可读文件
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            //step 2: 如果不被允许，则跳过此文件
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            //拼接超链接即可
            ret.append("<li>链接：<a href=\"");
            ret.append(name);
            ret.append("\">");
            ret.append(name);
            ret.append("</a></li>\r\n");
        }
        ret.append("</ul></body></html>\r\n");
        //构造结构，写入缓冲区
        ByteBuf buffer = Unpooled.copiedBuffer(ret, CharsetUtil.UTF_8);
        //进行写出操作
        response.content().writeBytes(buffer);
        //重置写出区域
        buffer.release();
        //使用ctx对象写出并且刷新到SocketChannel中去 并主动关闭连接(这里是指关闭处理发送数据的线程连接)
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }




    /** 检查请求
     * @Description
     * @param ctx
     * @param request
     * @return void
     * @throws
     * @date 21/3/24 15:58
     */
    public static void checkRequest(ChannelHandlerContext ctx,FullHttpRequest request){
        // 1、对请求的解码结果进行判断
        if(!request.decoderResult().isSuccess()){
            HandlerUtil.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        // 2、判断Http Method
        if(request.method()!= HttpMethod.GET){
            HandlerUtil.sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
    }
}
