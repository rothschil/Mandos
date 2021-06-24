package xyz.wongs.weathertop.java.concurrent.thread.day2.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @Description TODO
 * @Github <a>https://github.com/rothschil</a>
 * @date
 * @Version 1.0.0
 */
public class ConditionAlternately {

    static final Lock LOCK = new ReentrantLock();

    private static final int SIZE = 5;

    private static final Condition CD_1 = LOCK.newCondition();
    private static final Condition CD_2 = LOCK.newCondition();
    private static final Condition CD_3 = LOCK.newCondition();

    private static volatile Boolean BL_1 = true;
    private static volatile Boolean BL_2 = false;
    private static volatile Boolean BL_3 = false;

    public static void main(String[] args) {
        int size = 3;

    }
    static class ConditionA implements Runnable{
        @Override
        public void run() {
            try {
                if(BL_1){
                    for (int i = 0; i < SIZE; i++) {
                        System.out.println("AAAAAA");
                    }
                    BL_2 = true;
                    BL_1 = false;
                    CD_2.signal();
                } else {

                    CD_1.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ConditionB implements Runnable{
        @Override
        public void run() {
            try {
                if(BL_2){
                    for (int i = 0; i < SIZE; i++) {
                        System.out.println("BBBBB");
                    }
                    BL_3 = true;
                    BL_2 = false;
                    CD_3.signal();
                } else {
                    CD_2.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ConditionC implements Runnable{
        @Override
        public void run() {
            try {
                if(BL_3){
                    for (int i = 0; i < SIZE; i++) {
                        System.out.println("CCCCC");
                    }
                    BL_1 = true;
                    BL_3 = false;
                    CD_1.signal();
                } else {
                    CD_3.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
