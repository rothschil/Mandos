package xyz.wongs.weathertop.dependency.loopup;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName ObjectProviderDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/14 15:42
 * @Version 1.0.0
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ObjectProviderDemo.class);
        context.refresh();

        objectProvider(context);

        context.close();
    }

    @Bean
    public String helloWorld(){
        return "hello World";
    }

    private static void objectProvider(AnnotationConfigApplicationContext context) {
        ObjectProvider objectProvider = context.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }
}
