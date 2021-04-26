package xyz.wongs.weathertop.dependency.injection.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import xyz.wongs.weathertop.dependency.injection.BookHandler;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * 字段注入
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName AnnotationInjectionByFieldDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/23 14:35
 * @Version 1.0.0
 */
public class AnnotationInjectionByFieldDemo {

    @Autowired
    private BookHandler bookHandler;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationInjectionByFieldDemo.class);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
        String path = "classpath:/META-INF/dependency-lookup.xml";
        reader.loadBeanDefinitions(path);
        context.refresh();
        AnnotationInjectionByFieldDemo fieldDemo = context.getBean(AnnotationInjectionByFieldDemo.class);
        BookHandler book1 = fieldDemo.bookHandler;
        BookHandler book2 = context.getBean(BookHandler.class);
        System.out.println(book1==book2);
        context.close();
    }

    @Bean
    public BookHandler bookHandler(Book book){
        return new BookHandler(book);
    }
}
