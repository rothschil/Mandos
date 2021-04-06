package xyz.wongs.weathertop.ioc.dependency.lookup.overview.dependency.injection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.repository.BookRepository;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName LookupClazz
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/2 10:26
 * @Version 1.0.0
 */
public class InjectionClazz {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection.xml");
        // 依賴來源一：自定義Bean
        BookRepository repository = beanFactory.getBean("bookRepository",BookRepository.class);
//        System.out.println("【依賴注入（自定義Bean）】 "+repository.getUsers());
        // 依賴來源二：依賴注入（内建依賴）
        BeanFactory  bookBeanFactory = repository.getBeanFactory();
//
        // false 結論：以來查找的 beanFactory 和 依賴注入的 beanFactory 不是同一個。
//        System.out.println("【依賴注入（内建依賴）】"+(bookBeanFactory==beanFactory));

        ObjectFactory objectFactory = repository.getObjectFactory();

        System.out.println("【依賴注入（内建依賴）】"+(objectFactory.getObject()==beanFactory));

        // 依賴來源三：容器内建 Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("【内建非Bean】"+environment);

    }



}
