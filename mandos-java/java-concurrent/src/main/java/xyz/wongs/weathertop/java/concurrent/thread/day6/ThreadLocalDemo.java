package xyz.wongs.weathertop.java.concurrent.thread.day6;

import xyz.wongs.weathertop.java.concurrent.tool.ThreadPoolUtils;

import java.util.concurrent.ExecutorService;
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

    /**
     * 2、定义 ThreadLocal
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

    private String msg;

    public ThreadLocalDemo(String msg){
        this.msg = msg;
    }

    @Override
    public void run() {
        THREAD_LOCAL.remove();
        System.out.println(" ThreadName is "+Thread.currentThread().getName() +" Setting Before== >"+ msg);
        // 2.1、设置内容
        THREAD_LOCAL.set(msg);
        // 2.2、获取内容
        System.out.println(" ThreadName is "+Thread.currentThread().getName() +" Get == >"+ THREAD_LOCAL.get());
    }

    public static void main(String[] args) throws InterruptedException{
        // 1、创建线程池
        int size = 4;
        ExecutorService executorService = ThreadPoolUtils.doCreate(size,size,110,"LOCK");
        ThreadLocalDemo demo = null;
        for (int i = 0; i < size; i++) {
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
