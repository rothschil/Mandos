package xyz.wongs.weathertop.bean.instantiation;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BeanInstantiationDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/7 10:13
 * @Version 1.0.0
 */
public class BeanInstantiationDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation.xml");

        // 1、靜態方法方式實例化Bean
        Book staticBook = context.getBean("instace-static-method", Book.class);


        // 2、工廠方法方式實例化Bean
        Book beanFactoryBook = context.getBean("instace-bean-factory", Book.class);

        // 3、通过FactoryBean方式實例化Bean
        Book factoryBeanBook = context.getBean("instace-factory-bean", Book.class);

        System.out.println("【靜態方法方式實例化Bean】"+staticBook+" ;Hash = "+staticBook.hashCode());

        System.out.println("【工廠方法方式實例化Bean】"+beanFactoryBook+" ;Hash = "+beanFactoryBook.hashCode());

        System.out.println("【通过FactoryBean方式實例化Bean】"+factoryBeanBook+" ;Hash = "+factoryBeanBook.hashCode());

        System.out.println(staticBook==beanFactoryBook);
        System.out.println(beanFactoryBook==factoryBeanBook);
    }

}
