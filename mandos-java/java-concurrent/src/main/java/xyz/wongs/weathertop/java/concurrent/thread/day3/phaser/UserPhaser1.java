package xyz.wongs.weathertop.java.concurrent.thread.day3.phaser;

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
public class UserPhaser1 implements Runnable{

    private Phaser phaser;

    /**
     * 定义每个阶段需要几个子任务来完成
     */
    private int parties;
    /**
     * 任务需要阶段的数量
     */
    private int phases;

    public UserPhaser1(Phaser phaser, int parties, int phases) {
        this.phaser = phaser;
        this.parties = parties;
        this.phases = phases;
    }

    public UserPhaser1() {}

    @Override
    public void run() {
        for(int phase = 0; phase < phases; phase++) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("ThreadName "+Thread.currentThread().getName()+ "   [phase]="+ phase);
        }
    }

    public static void main(String[] args) {
        int parties =3;
        int phases = 4;
        final Phaser phaser = new Phaser(parties){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== Phase : " + phase + " ======");
                return registeredParties == 0;
            }
        };
        UserPhaser1 userPhaser = new UserPhaser1(phaser,parties,phases);
        // 根据阶段中的子任务来定义线程数量
        ExecutorService es = Executors.newFixedThreadPool(parties);
        for (int i = 0; i < parties; i++) {
            es.execute(userPhaser);
        }
        es.shutdown();
    }
}
