package xyz.wongs.weathertop.java.concurrent.thread.day5;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

public class UserFuture implements Callable<String> {

    private String exeStr;

    public UserFuture() {
    }

    public UserFuture(String exeStr) {
        this.exeStr = exeStr;
    }

    @Override
    public String call() throws Exception {
        int id = new Random().nextInt(5);
        LocalDateTime localDateTime = LocalDateTime.now();
        String resutl = exeStr+id;
        System.out.println("当前时间 "+localDateTime.getMinute()+":"+localDateTime.getSecond()+" 当前线程名: "+Thread.currentThread().getName()+ " 结果为"+ resutl);
        return resutl;
    }

    public static void main(String[] args) {
        FutureTask fs1 = new FutureTask(new UserFuture("李克强"));

        FutureTask fs2 = new FutureTask(new UserFuture("吴邦国"));

        ExecutorService executorService =  Executors.newFixedThreadPool(2);
        executorService.submit(fs1);
        executorService.submit(fs2);

        try {
            System.out.println(fs1.get());
            System.out.println(fs2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
