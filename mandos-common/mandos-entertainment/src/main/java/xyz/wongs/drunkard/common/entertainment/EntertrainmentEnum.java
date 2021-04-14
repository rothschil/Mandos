package xyz.wongs.drunkard.common.entertainment;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName EntertrainmentEnum
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/13 14:45
 * @Version 1.0.0
 */
public enum EntertrainmentEnum {

    MP4("mp4"),
    MOV("mov"),
    ASX("asx"),
    RM("rm"),
    RMVB("rmvb"),
    THGP("3gp"),
    DAT("dat"),
    FLV("flv"),
    VOB("vob"),
    ASF("asf");
    /**
     *
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    EntertrainmentEnum() {
    }

    EntertrainmentEnum(String type) {
        this.type = type;
    }
}
