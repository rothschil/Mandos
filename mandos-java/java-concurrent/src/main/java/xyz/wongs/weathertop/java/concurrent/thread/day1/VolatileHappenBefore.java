package xyz.wongs.weathertop.java.concurrent.thread.day1;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName VolatileHappenBefore
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/31 16:31
 * @Version 1.0.0
 */
public class VolatileHappenBefore {
    private int a = 0;
    private volatile boolean flag = true;

    public void read(){
        if(flag){
            int i =a;
        }
    }

    public void write(){
        a = 4;
        flag =false;
    }
}
