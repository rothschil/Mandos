package xyz.wongs.weathertop.java.concurrent.thread.day4;

import java.util.concurrent.*;

public class ThreadPoolUtils {

    public static ExecutorService getExecutorService(int coreSize,int maxSize,int queueSize){
        //处理器数量
        int poolSize = coreSize==0?Runtime.getRuntime().availableProcessors() * 2:coreSize;
        //队列大小
        maxSize= maxSize==0?512:maxSize;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(maxSize);
        //超出队列大小，选择的操作
        RejectedExecutionHandler policy = new ThreadPoolExecutor.AbortPolicy();
        return new ThreadPoolExecutor(poolSize, poolSize,0, TimeUnit.SECONDS, queue, policy);
    }

    public static void main(String[] args) {
        ExecutorService es = getExecutorService(1,1,1);

        ThreadTaskDemo t1 = new ThreadTaskDemo("t1");
        ThreadTaskDemo t2 = new ThreadTaskDemo("t2");
        ThreadTaskDemo t3 = new ThreadTaskDemo("t3");

        es.execute(t1);
        es.execute(t2);
        es.execute(t3);

        System.out.println("执行完毕！");
        es.shutdown();
    }





}
