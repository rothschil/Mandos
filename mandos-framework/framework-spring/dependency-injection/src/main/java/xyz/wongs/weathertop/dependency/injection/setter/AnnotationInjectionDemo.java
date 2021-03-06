package xyz.wongs.weathertop.dependency.injection.setter;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import xyz.wongs.weathertop.dependency.injection.BookHandler;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * Java注解配置注入
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName AnnotationInjectionDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/23 14:35
 * @Version 1.0.0
 */
public class AnnotationInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationInjectionDemo.class);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
        String path = "classpath:/META-INF/dependency-lookup.xml";
        reader.loadBeanDefinitions(path);

        context.refresh();
        BookHandler bookHandler = context.getBean(BookHandler.class);
        System.out.println(bookHandler);
        context.close();
    }

    @Bean
    public BookHandler bookHandler(Book book){
        BookHandler handler = new BookHandler();
        handler.setBook(book);
        return handler;
    }
}
