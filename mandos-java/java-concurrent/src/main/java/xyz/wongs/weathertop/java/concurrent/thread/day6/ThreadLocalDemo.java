package xyz.wongs.weathertop.java.concurrent.thread.day6;

import xyz.wongs.weathertop.java.concurrent.thread.day1.VolatileTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName ThreadLocalDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/6/1 9:43
 * @Version 1.0.0
 */
public class ThreadLocalDemo implements Runnable{

    // 2、定义 ThreadLocal
    private static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

    private String msg;

    public ThreadLocalDemo(String msg){
        this.msg = msg;
    }

    @Override
    public void run() {
        System.out.println(" ThreadName is "+Thread.currentThread().getName() +" Setting Before== >"+ msg);
        // 2.1、设置内容
        THREAD_LOCAL.set(msg);
        // 2.2、获取内容
        System.out.println(" ThreadName is "+Thread.currentThread().getName() +" Get == >"+ THREAD_LOCAL.get());
    }

    public static void main(String[] args) throws InterruptedException{
        // 1、创建线程池
        ExecutorService executorService = Executors.newScheduledThreadPool(3);
        ThreadLocalDemo demo = null;
        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(1);
            // 1.1、定义对象
            demo = new ThreadLocalDemo(" Number ["+i+" ] Runing");
            // 1.2、提交任务
            executorService.execute(demo);
        }

        // 3、关闭线程池
        executorService.shutdown();
    }
}
