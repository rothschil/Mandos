package xyz.wongs.weathertop.bean.instantiation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.wongs.weathertop.bean.instantiation.factory.BookFactory;
import xyz.wongs.weathertop.bean.instantiation.factory.DefaultBookFactory;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName InitializingBeanDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/14 09:50
 * @Version 1.0.0
 */
@Configuration
public class InitializingBeanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(InitializingBeanDemo.class);
        context.refresh();
        System.out.println("1、上下文启动完成");
        BookFactory factory  = context.getBean(BookFactory.class);
        System.out.println("2、"+ factory);
        System.out.println("3、准备关闭上下文");
        context.close();
        System.out.println("4、关闭上下文");
    }

    @Bean(initMethod = "initBookFactory",destroyMethod = "doDestroy")
    public BookFactory bookFactory(){
        return new DefaultBookFactory();
    }
}
