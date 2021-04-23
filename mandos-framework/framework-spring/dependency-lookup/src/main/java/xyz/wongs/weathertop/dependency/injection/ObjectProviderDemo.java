package xyz.wongs.weathertop.dependency.injection;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

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
        //objectProvider(context);
        //lookupIfAvaiable(context);

        lookupByStream(context);

        context.close();
    }

    private static void lookupByStream(AnnotationConfigApplicationContext context) {
        ObjectProvider<String> objectProvider = context.getBeanProvider(String.class);
//        Iterable<String> iterableObject = objectProvider;
//        for (String s:iterableObject){
//            System.out.println(s);
//        }
        objectProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvaiable(AnnotationConfigApplicationContext context) {
        ObjectProvider<Book> objectProvider = context.getBeanProvider(Book.class);
        Book book = objectProvider.getIfAvailable(Book::initBean);
        System.out.println("【当前对象 】 "+ book);
    }

    @Bean
    @Primary
    public String helloWorld(){
        return "hello World";
    }

    @Bean
    public String echo(){
        return "echo";
    }

    private static void objectProvider(AnnotationConfigApplicationContext context) {
        ObjectProvider<String> objectProvider = context.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }
}
