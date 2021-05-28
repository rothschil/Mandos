//package xyz.wongs.weathertop.java.concurrent.thread.day2;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import xyz.wongs.weathertop.java.concurrent.thread.day4.ThreadPoolUtils;
//import xyz.wongs.weathertop.domain.location.entity.Location;
//import xyz.wongs.weathertop.domain.location.service.LocationService;
//
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//
//
//public class UserCallable {
//
//    @Autowired
//    private LocationService locationService;
//
//
//    public void exct(){
//        ExecutorService es = ThreadPoolUtils.getExecutorService(1,2,3);
//
//        try {
//            List<Location> restul1 = es.submit(new CallableExp("合肥",locationService)).get();
//
//            List<Location> restul2 = es.submit(new CallableExp("肥西",locationService)).get();
//
//            List<Location> restul3 = es.submit(new CallableExp("淮南",locationService)).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//        }
//    }
//
//}
//
//
//
//class CallableExp implements Callable<List<Location>> {
//
//    private String localName;
//    private LocationService locationService;
//
//    public CallableExp(String localName){
//        this.localName = localName;
//    }
//
//    public CallableExp(String localName,LocationService locationService){
//        this.localName = localName;
//        this.locationService = locationService;
//    }
//
//    @Override
//    public List<Location> call() throws Exception {
//        return locationService.getLocationBySupLocalCode(localName);
//    }
//}