package xyz.wongs.weathertop.java.concurrent.thread.day3.phaser;

import xyz.wongs.weathertop.java.concurrent.tool.ThreadPoolUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName UserPhaser
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/6/1 17:12
 * @Version 1.0.0
 */
public class UserPhaser4{

    /**
     * 每个Phaser对象对应的工作线程（任务）数
     */
    private static final int TASK_PER_PHASER=4;

    public static void main(String[] args) throws IOException {
        int repeats = 3;
        final Phaser phaser = new Phaser(){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("---------------PHASE[" + phase + "],Parties[" + registeredParties + "] ---------------");
                return phase + 1 >= repeats  || registeredParties == 0;
            }
        };
        Tasker[] taskers = new Tasker[5];
        build(taskers, 0, taskers.length, phaser);

        ExecutorService es = ThreadPoolUtils.doCreate(taskers.length,taskers.length,"Phaser-");

        for (int i = 0; i < taskers.length; i++) {
            es.execute(taskers[i]);
        }
        es.shutdown();
    }

    private static void build(Tasker[] taskers, int lo, int hi, Phaser phaser){
        if (hi - lo > TASK_PER_PHASER) {
            for (int i = lo; i < hi; i += TASK_PER_PHASER) {
                int j = Math.min(i + TASK_PER_PHASER, hi);
                // 递归
                build(taskers, i, j, new Phaser(phaser));
            }
        } else {
            for (int i = lo; i < hi; ++i)
                taskers[i] = new Tasker(i, phaser);
        }
    }
}

class Tasker implements Runnable{
    private final int id;
    private final Phaser phaser;

    public Tasker(int id, Phaser phaser) {
        this.id =id;
        this.phaser = phaser;
        this.phaser.register();
    }

    @Override
    public void run() {
        while(!phaser.isTerminated()){
            int phase = phaser.arriveAndAwaitAdvance();
            System.out.println("ThreadName "+Thread.currentThread().getName()+ "   [当前阶段 phase]="+ phase);
        }
    }
}

