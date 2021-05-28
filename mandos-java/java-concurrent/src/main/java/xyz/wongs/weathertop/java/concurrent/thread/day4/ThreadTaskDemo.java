package xyz.wongs.weathertop.java.concurrent.thread.day4;


import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ThreadTaskDemo implements Runnable{


    private String value;

    public ThreadTaskDemo(){

    }

    public ThreadTaskDemo(String value){
        this.value=value;
    }

    @Override
    public void run() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间 "+localDateTime.getMinute()+":"+localDateTime.getSecond()+" 当前线程名: "+Thread.currentThread().getName()+" BEGIN "+value );
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
