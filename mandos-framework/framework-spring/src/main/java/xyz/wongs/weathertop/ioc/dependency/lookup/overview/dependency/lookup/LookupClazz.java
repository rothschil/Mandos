package xyz.wongs.weathertop.ioc.dependency.lookup.overview.dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.annotaion.Mandos;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

import java.util.Map;

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
//        Book book = (Book)beanFactory.getBean("book");
//        System.out.println(book.toString());
//        lookupByBeanName(beanFactory);
        lookupByType(beanFactory);

        lookupCollectionByType(beanFactory);

        lookupCollectionByAnnotation(beanFactory);
    }


    /** 注解查找
     * @Description
     * @param beanFactory
     * @return void
     * @throws
     * @date 21/4/2 14:47
     */
    private static void lookupCollectionByAnnotation(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String, Book> map = (Map)listableBeanFactory.getBeansWithAnnotation(Mandos.class);
            System.out.println("【注解方式查找】 "+ map);
        }

    }

    /** 集合查找
     * @Description
     * @param beanFactory
     * @return void
     * @throws
     * @date 21/4/2 14:47
     */
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String, Book> map = listableBeanFactory.getBeansOfType(Book.class);
            System.out.println("【集合方式查找】 "+ map);
        }
    }

    /** 通過類型獲取
     * @Description
     * @param beanFactory
     * @return void
     * @throws
     * @date 21/4/2 14:47
     */
    private static void lookupByType(BeanFactory beanFactory) {
        Book book = beanFactory.getBean(Book.class);
        System.out.println("【根據類型查找:】 "+ book.toString());
    }


    /** 通過bean 名稱獲取
     * @Description
     * @param beanFactory
     * @return void
     * @throws
     * @date 21/4/2 14:47
     */
    private static void lookupByBeanName(BeanFactory beanFactory) {
        ObjectFactory<Book> o  = (ObjectFactory<Book>)beanFactory.getBean("objectFactory");
        Book book = o.getObject();
        System.out.println("【根據Bean 名稱】 "+ book.toString() );

    }
}
