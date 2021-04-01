package xyz.wongs.weathertop.ioc;

import java.io.Serializable;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName DlsInfo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/1 16:49
 * @Version 1.0.0
 */
public class DlsInfo implements Serializable {

    String name;

    String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
