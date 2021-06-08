package xyz.wongs.weathertop.java.concurrent.thread.day1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName VolatileHappenBefore
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/31 16:31
 * @Version 1.0.0
 */
public class VolatileAtomicity implements Runnable{
    private static volatile int i = 0;

    @Override
    public void run() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileAtomicity volatileAtomicity = new VolatileAtomicity();
        for (int j = 0; j < 100; j++) {
            new Thread(volatileAtomicity).start();
        }
        while(Thread.activeCount()>1){
            System.out.println("==> "+ i);
        }
    }
}
