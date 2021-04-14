package xyz.wongs.weathertop.bean.instantiation;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.wongs.weathertop.bean.instantiation.factory.BookFactory;
import xyz.wongs.weathertop.bean.instantiation.factory.DefaultBookFactory;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName SpecialBeanInstantiationDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/7 10:13
 * @Version 1.0.0
 */
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation.xml");
        // 通过 ApplicationContext 返回 AutowireCapableBeanFactory
        AutowireCapableBeanFactory beanFactory  = context.getAutowireCapableBeanFactory();

        ServiceLoader<BookFactory> loader = context.getBean("specialLoaderBookFactoryBean",ServiceLoader.class);
//        displayServiceLoader(loader);
//        demoServiceLoader();

        BookFactory factory = beanFactory.createBean(DefaultBookFactory.class);
        System.out.println(factory.initBean());

        registerBeanDefinition(new DefaultListableBeanFactory(beanFactory));
        Book bok = beanFactory.getBean("book",Book.class);
        System.out.println(bok.toString());
    }

    public static void demoServiceLoader(){
        ServiceLoader<BookFactory> loader = ServiceLoader.load(BookFactory.class,Thread.currentThread().getContextClassLoader());
        displayServiceLoader(loader);
    }

    public static void displayServiceLoader(ServiceLoader<BookFactory> loader){
        Iterator<BookFactory> iterator = loader.iterator();
        while (iterator.hasNext()){
            BookFactory factory = iterator.next();
            System.out.println(factory.initBean());
        }
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry beanFactory){
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(Book.class);

        MutablePropertyValues values = new MutablePropertyValues();
        values.add("id",34).add("name","Thinking In Python");
        definition.setPropertyValues(values);
        beanFactory.registerBeanDefinition("book",definition);


    }
}
