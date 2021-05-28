package xyz.wongs.weathertop.java.concurrent.thread.day2;

import xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadPoolUtils;

import java.util.concurrent.ExecutorService;


public class UserRunnable {

    public static void main(String[] args) {
        ExecutorService es = ThreadPoolUtils.getExecutorService(1,2,2);
        for(int i=0;i<50;i++){
            es.execute(new Runnable() {
                public void run() {
                    System.out.println("ThreadName is " + Thread.currentThread().getName()+ " 获取许可");
                }
            });
        }


    }
}
