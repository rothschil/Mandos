package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx10upload;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import xyz.wongs.drunkard.common.enums.SysProperty;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ServerUpload
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 10:29$
 * @Version 1.0.0
 */
public class ServerUpload {

    private static ServerUpload serverUpload =null;

    private final EventLoopGroup processGroup;
    private final EventLoopGroup rwGroup;
    private final ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;

    /** 单例模式，使用DCL双重检查
     * @Description
     * @param url
     * @return xyz.wongs.weathertop.socket.rpc.pkg06netty.idx08download.ServerDownload
     * @throws
     * @date 21/3/26 09:28
     */
    public static ServerUpload getInstance(final SslContext sslCtx){
        if(null== serverUpload){
            // 类锁
            synchronized (ServerUpload.class){
                if(null== serverUpload){
                    serverUpload = new ServerUpload(sslCtx);
                }
            }
        }
        return serverUpload;
    }

    /** 构造方法
     * @Description
     * @param url
     * @return
     * @throws
     * @date 21/3/26 09:54
     */
    private ServerUpload(final SslContext sslCtx){
        processGroup = new NioEventLoopGroup();
        rwGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        buildBootstrap(sslCtx);
    }

    public void buildBootstrap(final SslContext sslCtx){
        bootstrap.group(processGroup,rwGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerUploadInitializer(sslCtx));
    }

    public void connect(final int port){
        try {
            channelFuture = bootstrap.bind("192.168.60.82",port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.processGroup.shutdownGracefully();
            this.rwGroup.shutdownGracefully();
        }
    }

    public ChannelFuture getChannelFuture(final int port){
        if(null==channelFuture){
            this.connect(port);
        }
        if(!channelFuture.channel().isActive()){
            this.connect(port);
        }
        return this.channelFuture;
    }

    public static void main(String[] args) throws Exception{
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }

        ServerUpload serverUpload = ServerUpload.getInstance(sslCtx);
        int port = 9099;
        serverUpload.getChannelFuture(port);
    }

    static final boolean SSL = System.getProperty(SysProperty.SSL.getCode()) != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));
}
