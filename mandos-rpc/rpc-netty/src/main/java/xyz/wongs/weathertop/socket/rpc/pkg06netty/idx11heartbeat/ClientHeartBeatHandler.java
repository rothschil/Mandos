package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx11heartbeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.message.HeartbeatMessage;
import xyz.wongs.drunkard.base.constant.Constant;
import xyz.wongs.drunkard.base.utils.StringUtils;
import xyz.wongs.drunkard.base.utils.security.SignatureUtils;
import xyz.wongs.drunkard.base.utils.thread.CoustomThreadFactory;
import xyz.wongs.drunkard.base.utils.thread.ThreadPoolUtils;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Res;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx11heartbeat.util.NodeInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ClientHeartBeatHandler
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:15$
 * @Version 1.0.0
 */
public class ClientHeartBeatHandler extends ChannelHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ClientHeartBeatHandler.class);

    private static final String SUCCESS ="200";

    private static final String SECRET_KEY = "cae85d3e70044ad8bb748fe205373c0b";

//    private ScheduledExecutorService TASK = ThreadPoolUtils.doCreate(1,"Client task");
    private ScheduledExecutorService TASK = Executors.newScheduledThreadPool(1);
    private ScheduledFuture scheduledFuture;

    /**
     * 主动向服务器发送认证信息
     */
    private InetAddress addr ;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            // 绑定证书 ，验证身份
            String key = "12345";
            String auth = ip+ Constant.HF_COLON+key;
            LOG.info("channelActive :"+auth);
            ctx.writeAndFlush(auth);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        try {
            if(msg instanceof String){
                String code = (String)msg;
                if(SUCCESS.equalsIgnoreCase(code)){
                    this.scheduledFuture  = this.TASK.scheduleWithFixedDelay(new HeartBeat(ctx),0,2, TimeUnit.SECONDS);
                } else{
                    LOG.error("The server response is unsuccessful！ "+ msg);
                }
            }
        }  catch (RejectedExecutionException e) {
            LOG.error("RejectedExecutionException"+msg);
        }  catch (NullPointerException e) {
            LOG.error("NullPointerException");
        } catch (IllegalArgumentException e) {
            LOG.error("IllegalArgumentException");
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    class HeartBeat implements Runnable{
        private final ChannelHandlerContext ctx;

        HeartBeat(final ChannelHandlerContext ctx){
            this.ctx=ctx;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setIp(addr.getHostAddress());
            Sigar sigar = new Sigar();
            try {

                CpuPerc cpuPerc = sigar.getCpuPerc();
                Map<String,Object> cpuPercMap = new HashMap<>();
                cpuPercMap.put("combined", cpuPerc.getCombined());
                cpuPercMap.put("user", cpuPerc.getUser());
                cpuPercMap.put("sys", cpuPerc.getSys());
                cpuPercMap.put("wait", cpuPerc.getWait());
                cpuPercMap.put("idle", cpuPerc.getIdle());
                nodeInfo.setCpuMap(cpuPercMap);


                Mem mem = sigar.getMem();
                Map<String,Object> memMap = new HashMap<>();
                memMap.put("total", mem.getTotal() / 1024L);
                memMap.put("used", mem.getUsed() / 1024L);
                memMap.put("free", mem.getFree() / 1024L);
                nodeInfo.setMemMap(memMap);
                LOG.warn(nodeInfo.toString());
                ctx.writeAndFlush(nodeInfo);
                long end = System.currentTimeMillis();
                LOG.error("耗时(毫秒)："+(end-start));
            } catch (SigarException e) {
                LOG.error(e.getMessage());
            }
        }
    }
}
