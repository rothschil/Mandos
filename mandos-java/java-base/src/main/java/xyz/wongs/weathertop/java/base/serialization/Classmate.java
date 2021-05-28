package xyz.wongs.weathertop.java.base.serialization;

import xyz.wongs.weathertop.java.base.reflect.Person;

import java.io.*;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName Classmate
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/28 9:20
 * @Version 1.0.0
 */
public class Classmate implements Serializable {

    private Long id;

    private Alumni alumni;

    public Classmate() {}

    public Classmate(Long id, Alumni alumni) {
        this.id = id;
        this.alumni = alumni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumni getPerson() {
        return alumni;
    }

    public void setPerson(Alumni alumni) {
        this.alumni = alumni;
    }

    public static void main(String[] args) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("Classmate.txt"));
            Alumni alumni = new Alumni(1L,"海贼王", "Desc");
            Classmate classmate = new Classmate(2L,alumni);
            oos.writeObject(classmate);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Alumni{

    private Long id;
    private String name;
    private String desc;

    public Alumni() {}

    public Alumni(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

