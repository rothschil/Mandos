package xyz.wongs.weathertop.java.concurrent.thread.day1;


import java.util.concurrent.TimeUnit;

public class UserVolatileDcl {

    private static volatile UserVolatileDcl userVolatile;

    private UserVolatileDcl(){}

    public static UserVolatileDcl getInstance() throws InterruptedException {
        if(null== userVolatile){
            synchronized (UserVolatileDcl.class) {
                if(null== userVolatile) {
                    TimeUnit.SECONDS.sleep(2);
                    userVolatile = new UserVolatileDcl();
                }
            }
        }
        return userVolatile;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {

           new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       System.out.println(UserVolatileDcl.getInstance().hashCode());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }).start();
        }
    }

}
