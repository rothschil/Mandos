package xyz.wongs.weathertop.java.concurrent.thread.day3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.*;

/**
 * @author WCNGS@QQ.COM
 * @ClassName UserSemaphone
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2020/3/30 9:04
 * @Version 1.0.0
 */
public class UserSemaphone {

    /**
     * 定义一个 Semaphone，维护一个许可证
     */
    static Semaphore sp = new Semaphore(1);

    public static void main(String[] args) {

        final UserSemaphone userSemaphone = new UserSemaphone();
        ExecutorService es = Executors.newFixedThreadPool(4);
        es.execute(new Student(sp, "李克强"));
        es.execute(new Student(sp, "习近平"));
        es.execute(new Student(sp, "毛泽东"));

        es.shutdown();
    }
}

class Student implements Runnable {
    static Log log = LogFactory.getLog(Student.class);
    private Semaphore sp = null;
    private String stuName;

    public Student(Semaphore sp, String stuName) {
        this.sp = sp;
        this.stuName = stuName;
    }

    public void run() {
        try {
            sp.acquire();
            log.info("ThreadName is " + Thread.currentThread().getName() + " 学生名：" + stuName + " 获取许可");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("ThreadName is " + Thread.currentThread().getName() + " 学生名：" + stuName + " 许可使用完毕，准备释放");
            sp.release();
        }
    }
}