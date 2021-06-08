package xyz.wongs.weathertop.java.concurrent.thread.day1;


import java.util.concurrent.TimeUnit;

public class UserVolatile {

    private static volatile UserVolatile instance;

    private UserVolatile(){}

    public static UserVolatile getInstance() throws InterruptedException {
        if(null== instance){
            synchronized (UserVolatile.class) {
                if(null== instance) {
                    TimeUnit.SECONDS.sleep(2);
                    instance = new UserVolatile();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       System.out.println(UserVolatile.getInstance().hashCode());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }).start();
        }
    }
}
