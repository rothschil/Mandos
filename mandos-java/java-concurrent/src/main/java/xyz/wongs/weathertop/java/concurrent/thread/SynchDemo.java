package xyz.wongs.weathertop.java.concurrent.thread;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName SynchDem
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/28 15:56
 * @Version 1.0.0
 */
public class SynchDemo {

    public static void main(String[] args) {
        synchronized (SynchDemo.class){
            System.out.println(""+Thread.currentThread().getName()+" ==>");
        }
    }

    public synchronized void getName() {
        System.out.println(""+Thread.currentThread().getName()+" ==>");
    }
}
