package xyz.wongs.weathertop.java.concurrent.thread.day3;

import xyz.wongs.weathertop.java.concurrent.tool.ThreadPoolUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName ExchangerDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/6/2 11:37
 * @Version 1.0.0
 */
public class ExchangerDemo {

    private static AtomicInteger proAto = new AtomicInteger(0);

    static class Producer implements Runnable{
        private List<String> buffer;
        private final Exchanger<List<String>> exchanger;
        public Producer(List<String> buffer,Exchanger<List<String>> exchanger){
            this.buffer=buffer;
            this.exchanger = exchanger;
        }
        @Override
        public void run() {
            boolean flag = true;
            while(flag){
                int times = proAto.incrementAndGet();
                buffer.add(" Buffer Value is "+times+" Number Of Times");
                try {
                    // 关键点1
                    exchanger.exchange(buffer);
                    TimeUnit.SECONDS.sleep(2);
                    if(times==20)
                        flag =false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ThreadName "+Thread.currentThread().getName()+ " [Producer] End");
        }
    }

    static class Consumer implements Runnable{
        private List<String> buffer ;
        private final Exchanger<List<String>> exchanger;

        public Consumer(List<String> buffer,Exchanger<List<String>> exchanger){
            this.buffer=buffer;
            this.exchanger = exchanger;
        }
        @Override
        public void run() {
            boolean flag = true;
            while(flag){
                try {
                    // 关键点2
                    buffer = exchanger.exchange(buffer);
                    int times = proAto.get();
                    System.out.println("ThreadName "+Thread.currentThread().getName()+ " [Consumer] 第"+times +" 次消费，消费内容为 "+ buffer.get(0));
                    buffer.remove(0);
                    if(times==20)
                        flag =false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }
    }

    public static void main(String[] args) {
        List<String> producerMap = new ArrayList<String>(5);
        List<String> consumerMap = new ArrayList<String>(5);
        Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
        int size = 2;
        ExecutorService es = ThreadPoolUtils.doCreate(size,size,"Exchanger-");
        es.execute(new Producer(producerMap,exchanger));
        es.execute(new Consumer(consumerMap,exchanger));
        es.shutdown();
    }
}
