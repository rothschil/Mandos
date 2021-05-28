package xyz.wongs.weathertop.java.concurrent.thread.day3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

public class UserCyclicBarrier {
    static Log log = LogFactory.getLog(UserCountDownLatch.class);
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有任务都完成");
            }
        });
        ExecutorService executorService =  Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.submit(new CyclicBarrierDemo(cyclicBarrier,"t-"+i));
        }

        log.info("主程序执行完成");

        executorService.shutdown();
    }
}

class CyclicBarrierDemo implements Runnable{

    static Log log = LogFactory.getLog(UserCountDownLatch.class);

    private String exeStr;
    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierDemo() {
    }

    public CyclicBarrierDemo(CyclicBarrier cyclicBarrier,String exeStr) {
        this.cyclicBarrier = cyclicBarrier;
        this.exeStr = exeStr;
    }

    @Override
    public void run() {
        try {
            int id = new Random().nextInt(6);
            TimeUnit.SECONDS.sleep(id);
            LocalDateTime localDateTime = LocalDateTime.now();
            String resutl = exeStr+"_"+id;
            log.info("当前时间 "+localDateTime.getMinute()+":"+localDateTime.getSecond()+" 当前线程名: "+Thread.currentThread().getName()+ " 结果为: "+ resutl);
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(BrokenBarrierException e){
            e.printStackTrace();
        }
    }
}
