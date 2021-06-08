package xyz.wongs.weathertop.java.concurrent.thread.day3;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


class CountDownLatchDemo implements Runnable{

    static Log log = LogFactory.getLog(CountDownLatchDemo.class);
    private String exeStr;

    private CountDownLatch countDownLatch;
    public CountDownLatchDemo() {
    }

    public CountDownLatchDemo(CountDownLatch countDownLatch,String exeStr) {
        this.countDownLatch = countDownLatch;
        this.exeStr = exeStr;
    }

    @Override
    public void run() {
        try {
            int id = new Random().nextInt(5);
            TimeUnit.SECONDS.sleep(id);
            LocalDateTime localDateTime = LocalDateTime.now();
            String resutl = exeStr+"_"+id;
            log.info("当前时间 "+localDateTime.getMinute()+":"+localDateTime.getSecond()+" 当前线程名: "+Thread.currentThread().getName()+ " 结果为: "+ resutl);
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class UserCountDownLatch {

    static Log log = LogFactory.getLog(UserCountDownLatch.class);

    public static void main(String[] args) {
        int size = 3;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        ExecutorService executorService =  Executors.newFixedThreadPool(size);
        String suffix = "T_";
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            executorService.execute(new CountDownLatchDemo(countDownLatch,suffix+i));
        }
        try {
            countDownLatch.await();
            long end = System.currentTimeMillis();
            log.info("执行完成 共耗时 "+(end-start)/1000 +" 秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
