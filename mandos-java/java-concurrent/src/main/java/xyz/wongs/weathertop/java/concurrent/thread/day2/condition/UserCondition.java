package xyz.wongs.weathertop.java.concurrent.thread.day2.condition;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserCondition {


    public static void main(String[] args) {

        final Lock lock = new ReentrantLock();
        final Condition putCondition  = lock.newCondition();
        final Condition takeCondition = lock.newCondition();

        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                120L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(100), new ThreadPoolExecutor.AbortPolicy());

        final ConditionDemo conditionDemo = new ConditionDemo(lock,putCondition,takeCondition);

        for (int i = 0; i < 100; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    conditionDemo.take();
                }
            });
        }

        for (int i = 0; i < 100; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    conditionDemo.put();
                }
            });
        }

        pool.shutdown();

    }

}

class ConditionDemo{

    private static int COUNT_SIZE = 10;

    private static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(COUNT_SIZE);

    private Lock lock;
    private Condition putCondition;
    private Condition takeCondition;

    public ConditionDemo() {
    }

    public ConditionDemo(Lock lock,Condition putCondition,Condition takeCondition) {
        this.lock = lock;
        this.putCondition = putCondition;
        this.takeCondition = takeCondition;
    }

    public void take() {
        lock.lock();
        try {
            while(queue.size()>0){
                LocalDateTime localDateTime = LocalDateTime.now();

                //取出数据
                Integer value = queue.take();
                int rd = new Random().nextInt(100);
                TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                System.out.println("TAKE 当前时间 "+localDateTime.getMinute()+":"+localDateTime.getSecond()+" 当前线程名: "+Thread.currentThread().getName()+ " 获取到的内容是 "+value+ " 当前剩余资源大小: "+queue.size());
                //唤醒添加线程，可以添加数据
                putCondition.signal();
                if(queue.size()==0){
                    System.out.println("TAKE 当前时间 "+localDateTime.getMinute()+":"+localDateTime.getSecond()+" 当前线程名: "+Thread.currentThread().getName()+ " 资源不够，休眠，开始唤醒添加操作 ");
                    takeCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void put() {
        lock.lock();
        try {


            while(queue.size() < COUNT_SIZE){
                LocalDateTime localDateTime = LocalDateTime.now();

                int rd = new Random().nextInt(100);
                queue.put(rd);
                TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                System.out.println("PUT 当前时间 " + localDateTime.getMinute() + ":" + localDateTime.getSecond() + " 当前线程名: " + Thread.currentThread().getName() + "添加的数据为 " + rd + " 当前资源大小: "+queue.size());
                takeCondition.signal();

                if (queue.size() == COUNT_SIZE) {
                    System.out.println("PUT 当前时间 " + localDateTime.getMinute() + ":" + localDateTime.getSecond() + " 当前线程名: " + Thread.currentThread().getName() + " 资源满，休眠，开始唤醒取出数据 ");
                    putCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

