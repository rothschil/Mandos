package xyz.wongs.weathertop.ioc.dependency.lookup.overview.mgr;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName LookupClazz
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/2 10:26
 * @Version 1.0.0
 */
public class LookupClazz {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup.xml");
        Book book = (Book)beanFactory.getBean("book");
        System.out.println(book.toString());
        lazyObjectBean(beanFactory);
        looupByType(beanFactory);
    }

    private static void looupByType(BeanFactory beanFactory) {
        Book book = beanFactory.getBean(Book.class);
        System.out.println("looupByType: "+ book.toString());
    }

    private static void lazyObjectBean(BeanFactory beanFactory) {
        ObjectFactory<Book> o  = (ObjectFactory<Book>)beanFactory.getBean("objectFactory");
        Book book = o.getObject();
        System.out.println("lazyObjectBean "+ book.toString() );

    }
}
