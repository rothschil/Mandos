package xyz.wongs.weathertop.java.concurrent.completable;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName QueryUtils
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/25 8:54
 * @Version 1.0.0
 */
public class QueryUtils {

    public String queryCar(Integer carId){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "car_desc";
    }
    public String queryJob(Integer jobId){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "job_desc";
    }
    public String queryHome(Integer homeId){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "home_desc";
    }
}
