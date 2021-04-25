package xyz.wongs.weathertop.java.concurrent.completable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName QueryUserService
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/25 8:58
 * @Version 1.0.0
 */
public class QueryUserService {

    private final Supplier<QueryUtils> queryUtilsSupplier = QueryUtils::new;

    public UserInfo converUserInfo(UserInfo userInfo) {
        QuerySuppiler querySuppiler1 = new QuerySuppiler(userInfo.getCarId(), "car", queryUtilsSupplier.get());
        CompletableFuture<String> getCarDesc = CompletableFuture.supplyAsync(querySuppiler1);
        getCarDesc.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String carDesc) {
                userInfo.setCarDes(carDesc);
            }
        });

        QuerySuppiler querySuppiler2 = new QuerySuppiler(userInfo.getHomeId(), "home", queryUtilsSupplier.get());
        CompletableFuture<String> getHomeDesc = CompletableFuture.supplyAsync(querySuppiler2);
        getHomeDesc.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                userInfo.setHomeDes(s);
            }
        });

        QuerySuppiler querySuppiler3 = new QuerySuppiler(userInfo.getJobId(), "job", queryUtilsSupplier.get());
        CompletableFuture<String> getJobDesc = CompletableFuture.supplyAsync(querySuppiler3);
        getJobDesc.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                userInfo.setJobDes(s);
            }
        });

        CompletableFuture<Void> getUserInfo = CompletableFuture.allOf(getCarDesc,getHomeDesc,getJobDesc);
        getUserInfo.thenAccept(new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                System.out.println("【converUserInfo】全部完成查询" );
            }
        });
        getUserInfo.join();
        return userInfo;
    }

    public UserInfo converUserInfo2(UserInfo userInfo) {
        Map<String,FutureTask<String>> futureMap = new ConcurrentHashMap<String,FutureTask<String>>();

        Callable<String> homeCallable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                return queryUtilsSupplier.get().queryHome(userInfo.getHomeId());
            }
        };
        FutureTask<String> getHomeDesc=new FutureTask<String>(homeCallable);
        new Thread(getHomeDesc).start();
        futureMap.put("homeCallable",getHomeDesc);

        Callable<String> carCallable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                return queryUtilsSupplier.get().queryCar(userInfo.getCarId());
            }
        };
        FutureTask<String> getCarDesc=new FutureTask<String>(carCallable);
        new Thread(getCarDesc).start();
        futureMap.put("carCallable",getCarDesc);


        Callable<String> jobCallable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                return queryUtilsSupplier.get().queryCar(userInfo.getJobId());
            }
        };
        FutureTask<String> getJobDesc=new FutureTask<>(jobCallable);
        new Thread(getJobDesc).start();
        futureMap.put("jobCallable",getJobDesc);

        try {
            userInfo.setHomeDes(futureMap.get("homeCallable").get());
            userInfo.setCarDes(futureMap.get("carCallable").get());
            userInfo.setJobDes(futureMap.get("jobCallable").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public static void main(String[] args) {
        long begin= System.currentTimeMillis();
        createCollection();
        long end= System.currentTimeMillis();

        System.out.println("【总共耗时】 = "+ (end-begin)/1000 + " 秒");
    }

    public static void createCollection(){
        List<UserInfo> userInfoList= Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i <=20; i++) {
            UserInfo userInfo=new UserInfo();
            userInfo.setId(i);
            userInfo.setName("username"+i);
            userInfo.setCarId(i);
            userInfo.setJobId(i);
            userInfo.setHomeId(i);
            userInfoList.add(userInfo);
        }

        executorByCompletableFuture(userInfoList);
//        executorByCallable(userInfoList);
    }

    public static void executorByCallable(List<UserInfo> list){
        list.stream().map(userInfo -> {
            QueryUserService service = new QueryUserService();
            userInfo = service.converUserInfo2(userInfo);
            System.out.println("【converUserInfo2】 该对象完成查询"+ userInfo.toString());
            return userInfo;
        }).collect(Collectors.toList());
    }

    public static void executorByCompletableFuture(List<UserInfo> list){
        list.stream().map(userInfo -> {
            QueryUserService service = new QueryUserService();
            userInfo = service.converUserInfo(userInfo);
            System.out.println("【converUserInfo】 该对象完成查询"+ userInfo.toString());
            return userInfo;
        }).collect(Collectors.toList());
    }

}
