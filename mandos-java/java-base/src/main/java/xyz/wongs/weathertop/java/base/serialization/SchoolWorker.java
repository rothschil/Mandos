package xyz.wongs.weathertop.java.base.serialization;

import java.io.*;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName SchoolWorker
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/28 9:39
 * @Version 1.0.0
 */
public class SchoolWorker implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id ;

    private Worker worker;

    public SchoolWorker() {
    }

    public SchoolWorker(Long id, Worker worker) {
        System.out.println("这是反序列化!!!");
        this.id = id;
        this.worker = worker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String toString() {
        return "SchoolWorker{" +
                "id=" + id +
                ", worker=" + worker +
                '}';
    }

    /** 两次序列化前后
     * @Description
     * @param
     * @return void
     * @throws
     * @date 2021/5/28 10:15
     */
    public static void problem(){

        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            String file = "SchoolWorker.txt";
            oos = new ObjectOutputStream(new FileOutputStream(file));
            ois = new ObjectInputStream(new FileInputStream(file));
            // 第一次序列化
            Worker worker = new Worker(1L,"索隆");
            SchoolWorker schoolWorker = new SchoolWorker(2L,worker);
            System.out.println(" 【第一次序列化】 "+schoolWorker);
            oos.writeObject(schoolWorker);

            // 修改成员变量属性
            // 第二次序列化
            schoolWorker.setId(100L);
            System.out.println(" 【第二次序列化】 "+schoolWorker);
            oos.writeObject(schoolWorker);


            // 反序列化
            SchoolWorker schoolWorker1 = (SchoolWorker)ois.readObject();
            SchoolWorker schoolWorker2 = (SchoolWorker)ois.readObject();

            System.out.println(schoolWorker1==schoolWorker1);
            System.out.println(schoolWorker1.getId()==schoolWorker2.getId());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        serializable();
//        deserialization();
//        problem();
        externalizable();
    }

    public static void externalizable(){
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            String file = "Book.txt";
            oos = new ObjectOutputStream(new FileOutputStream(file));
            ois = new ObjectInputStream(new FileInputStream(file));
            Book book = new Book(1L,"索隆");
            oos.writeObject(book);
            // 反序列化
            Book book1 = (Book)ois.readObject();
            System.out.println(book1.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** 反序列化
     * @Description
     * @param
     * @return void
     * @throws
     * @date 2021/5/28 9:44
     */
    public static void deserialization(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("SchoolWorker.txt"));
            SchoolWorker schoolWorker = (SchoolWorker)ois.readObject();
            System.out.println(schoolWorker.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** 序列化
     * @Description
     * @param
     * @return void
     * @throws
     * @date 2021/5/28 9:44
     */
    public static void serializable(){

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("SchoolWorker.txt"));
            Worker worker = new Worker(1L,"索隆");
            SchoolWorker schoolWorker = new SchoolWorker(2L,worker);
            oos.writeObject(schoolWorker);
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

class Worker implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;


    public Worker() {}

    public Worker(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


class Book implements Externalizable{
    private Long id;
    private String name;

//    public Book() {
//        System.out.println(" Book() 构造方法");
//    }

    public Book(Long id, String name) {
        System.out.println(" Book(Long id, String name) 构造方法");
        this.id = id;
        this.name = name;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        StringBuffer reverse = new StringBuffer(name).reverse();
        System.out.println(reverse.toString());
        out.writeObject(reverse);
        out.writeLong(id);
    }
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = ((StringBuffer) in.readObject()).reverse().toString();
        System.out.println(name);
        this.id = in.readLong();
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

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}