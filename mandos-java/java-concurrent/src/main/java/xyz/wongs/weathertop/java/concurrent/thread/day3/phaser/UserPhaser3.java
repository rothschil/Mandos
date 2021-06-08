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
public class UserPhaser3 implements Runnable{

    private final Phaser phaser;

    public UserPhaser3(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        while(!phaser.isTerminated()){
            int phase = phaser.arriveAndAwaitAdvance();
            System.out.println("ThreadName "+Thread.currentThread().getName()+ "   [当前阶段 phase]="+ phase);
        }
    }

    public static void main(String[] args) throws IOException {
        // 定义循环的次数
        int repeats = 3;
        // 定义阶段的参与者，覆盖 onAdvance 方法，当最后一个参与者到达时，会触发该方法，
        // params：phase 表示到达时的phase值，
        // params：registeredParties 表示到达时的参与者数量，返回true表示需要终止Phaser。
        final Phaser phaser = new Phaser(){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("---------------PHASE[" + phase + "],Parties[" + registeredParties + "] ---------------");
                // phase + 1 >= repeats ，来控制阶段（phase）数的上限为2（从0 开始计算），最终控制了每个线程的执行任务次数为repeats次。
                return phase + 1 >= repeats  || registeredParties == 0;
            }
        };
        UserPhaser3 userPhaser = new UserPhaser3(phaser);

        int size = 4;
        // 根据阶段中的子任务来定义线程数量
        ExecutorService es = ThreadPoolUtils.doCreate(size,size,"Phaser-");
        for (int i = 0; i < size; i++) {
            // 注册参与者线程
            phaser.register();
            es.execute(userPhaser);
        }
        es.shutdown();
    }
}
