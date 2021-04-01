package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx11heartbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.MarshallingCodeCFactory;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ServerHeartBeatInitializer
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/26$ 10:31$
 * @Version 1.0.0
 */
public class ServerHeartBeatInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public ServerHeartBeatInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
        ch.pipeline().addLast(new ServerHeartBeatHandler());
    }
}
