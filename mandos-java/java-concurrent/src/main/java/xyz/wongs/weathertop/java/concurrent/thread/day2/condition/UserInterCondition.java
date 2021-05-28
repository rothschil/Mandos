package xyz.wongs.weathertop.java.concurrent.thread.day2.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserInterCondition {

    public static void main(String[] args) {

        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                120L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(100), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 3; i++) {
            pool.execute(new ConditionDemo2());
        }
        pool.shutdown();

    }

}

class ConditionDemo2 implements Runnable{

    Lock lock = new ReentrantLock();
    Condition ca  = lock.newCondition();
    Condition cb = lock.newCondition();
    Condition cc = lock.newCondition();

    ConditionDemo2(){}


    public void metA() {
        lock.lock();
        try {
            while(index1!=0){
                ca.await();
            }
            for (int i = 0; i < 3; i++) {
                System.out.println(" A "+Thread.currentThread().getName() + " 序号 " + i+" index1 第几 "+index1+ " 轮");
            }
            index1=(index1+1)%3;
            cb.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void metB() {
        lock.lock();
        try {
            while(index1!=1){
                cb.await();
            }
            for (int i = 0; i < 4; i++) {
                System.out.println(" B "+Thread.currentThread().getName() + " 序号 " + i+" index1 第几 "+index1+ " 轮");
            }
            index1=(index1+1)%3;
            cc.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void metC() {
        lock.lock();
        try {
            while(index1!=2){
                cc.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(" C "+Thread.currentThread().getName() + " 序号 " + i+" index1 第几 "+index1+ " 轮");
            }
            index1=(index1+1)%3;
            ca.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {

        for (int i = 0; i < 3; i++) {
            if(index0==1){
                metA();
            } else if(index0==2){
                metB();
            } else {
                metC();
            }
            index0 = (index0+1)%3;
        }
    }

    int index0 = 1;
    int index1 = 0;
}