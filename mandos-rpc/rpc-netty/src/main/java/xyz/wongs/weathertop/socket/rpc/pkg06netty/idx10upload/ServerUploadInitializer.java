package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx10upload;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ServerUploadInitializer$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/26$ 10:31$
 * @Version 1.0.0
 */
public class ServerUploadInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public ServerUploadInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }

        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());

        // Remove the following line if you don't want automatic content compression.
        pipeline.addLast(new HttpContentCompressor());
        pipeline.addLast(new ServerUploadHandler());
    }
}
