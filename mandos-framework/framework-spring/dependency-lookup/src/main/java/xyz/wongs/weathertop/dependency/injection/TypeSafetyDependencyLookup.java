package xyz.wongs.weathertop.dependency.injection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * @ClassName TypeSafetyDependencyLookup
 * @Description 
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/22 11:15
 * @Version 1.0.0
*/
public class TypeSafetyDependencyLookup {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ObjectProviderDemo.class);
        context.refresh();
        // BeanFactory#getBean
        disableBeanFactoryGetBean(context);
        // ObjectFactory#getObject
        disableObjectFactoryGetBean(context);
        // ObjectProvider#getIfAvailable
        disableObjectProviderGetIfAvailable(context);
        // ListableBeanFactory#getBeansOfType
        disableListableBeanFactoryGetBeansOfType(context);
        // ObjectProvider#stream
        disableObjectProviderByStream(context);
        context.close();
    }


    /**
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 11:15
     */
    private static void disableObjectProviderByStream(AnnotationConfigApplicationContext context) {
        ObjectProvider<Book> objectProvider = context.getBeanProvider(Book.class);
        printBeanException("ObjectProvider",()->{
            objectProvider.stream().forEach(System.out::println);
        });
    }

    /**
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 11:15
     */
    private static void disableListableBeanFactoryGetBeansOfType(AnnotationConfigApplicationContext context) {
        ListableBeanFactory beanFactory = context.getBeanFactory();
        printBeanException("ListableBeanFactory",()->{
            beanFactory.getBeansOfType(Book.class);
        });
    }

    /**
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 11:15
     */
    private static void disableObjectProviderGetIfAvailable(AnnotationConfigApplicationContext context) {
        ObjectProvider<Book> objectProvider = context.getBeanProvider(Book.class);
        printBeanException("ObjectProvider",()->{
            objectProvider.getIfAvailable();
        });
    }

    /**
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 11:15
     */
    private static void disableObjectFactoryGetBean(AnnotationConfigApplicationContext context) {
        ObjectFactory<Book> bookObjectFactory = context.getBeanProvider(Book.class);
        printBeanException("ObjectFactory",()->{
            bookObjectFactory.getObject();
        });
    }

    /**
     * @Description
     * @param beanFactory
     * @return void
     * @throws
     * @date 2021/4/22 11:14
     */
    private static void disableBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeanException("BeanFactory",()->{
            Book book = beanFactory.getBean(Book.class);
        });
    }

    public static void printBeanException(String resource ,Runnable runnable){
        try {
            System.err.println("===============================");
            System.err.println(resource);
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
