package xyz.wongs.weathertop.java.concurrent.thread.day4.exception;

import xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadTaskDemo;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class CoustomRejectedExecutionHandler implements RejectedExecutionHandler {


    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof ThreadTaskDemo) {
            ThreadTaskDemo thTk = (ThreadTaskDemo) r;
            //为了演示用，所以直接打印，勿模仿。正式场景下应该持久化或者写入日志！！！
            System.out.println("当前任务 "+ thTk.getValue()+" 执行失败！");
        }
    }
}
