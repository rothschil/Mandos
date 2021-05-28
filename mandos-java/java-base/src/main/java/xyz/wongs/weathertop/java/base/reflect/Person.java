package xyz.wongs.weathertop.java.base.reflect;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName Persion
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/27 9:50
 * @Version 1.0.0
 */
public class Person {

    private Long id;

    private String prName;

    private String desc;

    public Person() {
    }

    public Person(Long id, String prName, String desc) {
        this.id = id;
        this.prName = prName;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", prName='" + prName + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
