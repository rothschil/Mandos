package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx11heartbeat.util;

import java.io.Serializable;
import java.util.Map;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NodeInfo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/30 16:48
 * @Version 1.0.0
 */
public class NodeInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * IP 节点信息
     */
    private String ip;

    /**
     * 内存信息
     */
    private Map<String,Object> memMap;

    /**
     * CPU信息
     */
    private Map<String,Object> cpuMap;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, Object> getMemMap() {
        return memMap;
    }

    public void setMemMap(Map<String, Object> memMap) {
        this.memMap = memMap;
    }

    public Map<String, Object> getCpuMap() {
        return cpuMap;
    }

    public void setCpuMap(Map<String, Object> cpuMap) {
        this.cpuMap = cpuMap;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "ip='" + ip + '\'' +
                ", memMap=" + memMap.toString() +
                ", cpuMap=" + cpuMap.toString() +
                '}';
    }
}
