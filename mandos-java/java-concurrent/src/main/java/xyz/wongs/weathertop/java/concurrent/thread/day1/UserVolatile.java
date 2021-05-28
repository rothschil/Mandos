package xyz.wongs.weathertop.java.concurrent.thread.day1;


import java.util.concurrent.TimeUnit;

public class UserVolatile {

    private static UserVolatile userVolatile;

    private UserVolatile(){}

    public static UserVolatile getInstance() throws InterruptedException {
        if(null== userVolatile){
            synchronized (UserVolatile.class) {
                TimeUnit.SECONDS.sleep(2);
                userVolatile = new UserVolatile();
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
                       System.out.println(UserVolatile.getInstance().hashCode());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }).start();
        }
    }

}
