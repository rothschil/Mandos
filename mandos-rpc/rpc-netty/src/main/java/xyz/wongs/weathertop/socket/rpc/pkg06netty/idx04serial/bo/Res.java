package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo;

import java.io.Serializable;

/**
 * @author WCNGS@QQ.COM
 * @ClassName Res$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 17:24$
 * @Version 1.0.0
 */
public class Res implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
