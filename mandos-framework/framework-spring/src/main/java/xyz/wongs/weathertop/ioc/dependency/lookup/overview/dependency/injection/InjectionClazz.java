package xyz.wongs.weathertop.ioc.dependency.lookup.overview.dependency.injection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup.xml");

    }



}
