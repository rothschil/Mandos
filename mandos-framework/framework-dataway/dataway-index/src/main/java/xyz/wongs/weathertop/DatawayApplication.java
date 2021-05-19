package xyz.wongs.weathertop;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName DatawayApplication
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/13 10:01
 * @Version 1.0.0
 */
@EnableHasor
@EnableHasorWeb
@SpringBootApplication(scanBasePackages = {"xyz.wongs.weathertop"})
public class DatawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatawayApplication.class, args);
    }
}
