package xyz.wongs.weathertop.java.base.time;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName TimeDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/28 15:32
 * @Version 1.0.0
 */
public class TimeDemo {

    public static void main(String[] args) {
        //获取当前时区的日期
        LocalDate localDate = LocalDate.now();
        System.out.println("localDate: " + localDate);
        //时间
        LocalTime localTime = LocalTime.now();
        System.out.println("localTime: " + localTime);
        //根据上面两个对象，获取日期时间
        LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
        System.out.println("localDateTime: " + localDateTime);
        //使用静态方法生成此对象
        LocalDateTime localDateTime2 = LocalDateTime.now();
        System.out.println("localDateTime2: " + localDateTime2);

        //格式化时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        System.out.println("格式化之后的时间: " + localDateTime2.format(formatter));

        //转化为时间戳(秒)
        long epochSecond = localDateTime2.toEpochSecond(ZoneOffset.of("+8"));
        //转化为毫秒
        long epochMilli = localDateTime2.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
        System.out.println("时间戳为:(秒) " + epochSecond + "; (毫秒): " + epochMilli);

        //时间戳(毫秒)转化成LocalDateTime
        LocalDateTime localDateTime3 = getDateTime(epochMilli,true);
        System.out.println("时间戳(毫秒)转化成LocalDateTime: " + localDateTime3.format(formatter));
        //时间戳(秒)转化成LocalDateTime
        LocalDateTime localDateTime4 = getDateTime(epochSecond,false);
        System.out.println("时间戳(秒)转化成LocalDateTime: " + localDateTime4.format(formatter));
    }

    public static LocalDateTime getDateTime(long source,boolean isMilli){
        Instant instant = null;
        if(isMilli){
            instant = Instant.ofEpochMilli(source);
        } else {
            instant= Instant.ofEpochSecond(source);
        }
        return  LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }
}
