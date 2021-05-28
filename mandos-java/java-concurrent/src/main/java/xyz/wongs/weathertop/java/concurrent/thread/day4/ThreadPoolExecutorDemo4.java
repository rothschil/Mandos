package xyz.wongs.weathertop.java.concurrent.thread.day4;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

public class ThreadPoolExecutorDemo4 {

    public static void main(String[] args) {
        ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build();

        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

    }
}
