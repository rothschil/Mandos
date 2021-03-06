package xyz.wongs.weathertop.java.concurrent.thread.day7;

import xyz.wongs.weathertop.java.concurrent.tool.ThreadPoolUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @Description TODO
 * @Github <a>https://github.com/rothschil</a>
 * @date  
 * @Version 1.0.0
 */
public class ReentrantLockDemo {
    private static final ReentrantLock LOCK = new ReentrantLock(true);

    private static volatile int CONS=0;


    public static void main(String[] args) throws InterruptedException {

        int size = 4;
        ExecutorService es = ThreadPoolUtils.doCreate(size,size,110,"LOCK");
        int threadSize = 31;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize-1);
        for (int j= 1; j < threadSize; j++) {
            es.submit(new Runner(j,countDownLatch));
        }
        countDownLatch.await();
        System.out.println( "执行结果 "+ CONS );
        es.shutdown();
    }


    static class Runner implements Runnable{
        int times;
        CountDownLatch countDownLatch;
        Runner(int times,CountDownLatch countDownLatch){
            this.times=times;
            this.countDownLatch =countDownLatch;
        }
        @Override
        public void run() {
            LOCK.lock();
            try {
                CONS = CONS+times;
                System.out.println(Thread.currentThread().getName() + " 获取到锁 "+ times +" Times");
            } finally {
                LOCK.unlock();
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + " 序号为 "+ times +" 锁释放");
            }
        }
    }
}
