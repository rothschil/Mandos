package xyz.wongs.weathertop.java.concurrent.thread.day4;

import xyz.wongs.weathertop.java.concurrent.thread.day4.exception.CoustomRejectedExecutionHandler;

import java.util.concurrent.*;

public class ThreadPoolExecutorDemo2 {

    public static ExecutorService getExecutorService(int coreSize,int maxSize,BlockingQueue queue){
//        RejectedExecutionHandler policy = new ThreadPoolExecutor.AbortPolicy();
        RejectedExecutionHandler policy = new CoustomRejectedExecutionHandler();
        return new ThreadPoolExecutor(coreSize, maxSize,0, TimeUnit.SECONDS, queue, policy);
    }

    public static void main(String[] args) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(3);
//        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        ExecutorService es = getExecutorService(1,1,queue);

        xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo t1 = new xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo("t1");
        xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo t2 = new xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo("t2");
        xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo t3 = new xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo("t3");
        xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo t4 = new xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo("t4");
        xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo t5 = new xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo("t5");
        xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo t6 = new xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo("t6");
        xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo t7 = new xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo("t7");

        es.execute(t1);
        es.execute(t2);
        es.execute(t3);
        es.execute(t4);
        es.execute(t5);
        es.execute(t6);
        es.execute(t7);

        System.out.println("执行完毕！");
        es.shutdown();
    }
}
