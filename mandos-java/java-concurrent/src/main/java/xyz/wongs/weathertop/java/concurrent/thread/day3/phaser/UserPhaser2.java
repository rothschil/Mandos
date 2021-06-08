package xyz.wongs.weathertop.java.concurrent.thread.day3.phaser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName UserPhaser
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/6/1 17:12
 * @Version 1.0.0
 */
public class UserPhaser2 implements Runnable{

    private Phaser phaser;

    public UserPhaser2(Phaser phaser) {
        this.phaser = phaser;
    }

    public UserPhaser2() {}

    @Override
    public void run() {
        int phase = phaser.arriveAndAwaitAdvance();
        System.out.println("ThreadName "+Thread.currentThread().getName()+ "   [当前阶段 phase]="+ phase);
    }

    public static void main(String[] args) throws IOException {
        // 定义阶段的参与者
        final Phaser phaser = new Phaser(1);
        UserPhaser2 userPhaser = new UserPhaser2(phaser);
        int size = 5;
        // 根据阶段中的子任务来定义线程数量
        ExecutorService es = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            // 注册参与者线程
            phaser.register();
            es.execute(userPhaser);
        }
        System.out.println("Press ENTER to continue");
        // 创造外部条件，这里就是等待输入
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
        // 打开开关
        phaser.arriveAndDeregister();
        System.out.println("主线程开关已打开");
        es.shutdown();
    }
}
