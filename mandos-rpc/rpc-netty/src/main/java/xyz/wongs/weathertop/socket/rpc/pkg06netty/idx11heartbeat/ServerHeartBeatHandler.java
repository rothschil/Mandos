package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx11heartbeat;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wongs.drunkard.base.constant.Constant;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx11heartbeat.util.NodeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ServerHeartBeatHandler
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:02$
 * @Version 1.0.0
 */
public class ServerHeartBeatHandler extends ChannelHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ServerHeartBeatHandler.class);

    private static final String SUCCESS ="200";
    private static final String FAILURE ="Connection failed";


    /**
     * 身份记录表集合
     */
    private static Map<String,String> IDENTITYS = new HashMap<String,String>();

    static {
        IDENTITYS.put("192.168.244.1", "12345");
        IDENTITYS.put("192.168.60.82", "12345");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       if(msg instanceof String) {
           // 1、查验身份
           authenticate((String)msg,ctx);
       } else if(msg instanceof NodeInfo){
           printNodeInfo((NodeInfo)msg);
           ctx.writeAndFlush(SUCCESS);
       } else {
           LOG.error("失败断开连接 ");
           ctx.writeAndFlush(FAILURE).addListener(ChannelFutureListener.CLOSE);
       }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * @Description
     * @param msg
     * @param ctx
     * @return void
     * @throws
     * @date 21/3/30 16:57
     */
    public boolean authenticate(String msg, ChannelHandlerContext ctx){
        String [] ret = ((String) msg).split(Constant.HF_COLON);
        String auth = IDENTITYS.get(ret[0]);
        if(auth != null && auth.equals(ret[1])){
            ctx.writeAndFlush(SUCCESS);
            return true;
        } else {
            LOG.error("查验身份: 失败 ");
            ctx.writeAndFlush(FAILURE).addListener(ChannelFutureListener.CLOSE);
            return false;
        }
    }


    /**
     * @Description
     * @param nodeInfo
     * @return void
     * @throws
     * @date 21/3/30 16:52
     */
    private void printNodeInfo(NodeInfo nodeInfo){
        try {
            LOG.warn(nodeInfo.getIp());
            Map<String,Object> cpuPerc = nodeInfo.getCpuMap();
            LOG.warn("Cpu:Combined"+cpuPerc.get("combined")+" User: "+cpuPerc.get("user")+" Sys: "+cpuPerc.get("sys")+" Wait: "+cpuPerc.get("wait") +" Idle: "+cpuPerc.get("idle"));
            Map<String,Object> mem = nodeInfo.getMemMap();
            LOG.warn("Mem:Total"+cpuPerc.get("total")+" Used: "+cpuPerc.get("used")+" Free: "+cpuPerc.get("free"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
