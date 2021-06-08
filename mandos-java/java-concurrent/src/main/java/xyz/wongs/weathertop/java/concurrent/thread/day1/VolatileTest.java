package xyz.wongs.weathertop.java.concurrent.thread.day1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName VolatileTest
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/31 15:41
 * @Version 1.0.0
 */
public class VolatileTest implements Runnable{

    private static volatile boolean FLAG = false;

    @Override
    public void run() {
        while(!FLAG){
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.execute(new VolatileTest());
        TimeUnit.SECONDS.sleep(2);
        FLAG = true;
        executorService.shutdown();
    }
}
