package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx11heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.MarshallingCodeCFactory;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Req;

import java.util.concurrent.TimeUnit;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClient$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:12$
 * @Version 1.0.0
 */
public class ClientHeartBeat {

    private static ClientHeartBeat clientHeartBeat =null;


    public static ClientHeartBeat getInstance(){
        if(null== clientHeartBeat){
            // 类锁
            synchronized (ClientHeartBeat.class){
                if(null== clientHeartBeat){
                    clientHeartBeat = new ClientHeartBeat();
                }
            }
        }
        return clientHeartBeat;
    }

    private EventLoopGroup processGroup;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;

    private ClientHeartBeat(){
        processGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(processGroup).channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(new ClientHeartBeatHandler());
                    }
                });
    }

    public ChannelFuture getChannelFuture(int port){
        if(null==this.channelFuture){
            this.connect(port);
        }

        if(!this.channelFuture.channel().isActive()){
            this.connect(port);
        }
        return this.channelFuture;
    }

    public void connect(int port){
        try {
            System.out.println("远程服务器已经连接, 可以进行数据交换..");
            this.channelFuture = bootstrap.connect("192.168.60.82",port).sync();
            this.channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            processGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        final ClientHeartBeat clientHeartBeat = ClientHeartBeat.getInstance();
        final int port = 8765;
        clientHeartBeat.getChannelFuture(port);

    }
}
