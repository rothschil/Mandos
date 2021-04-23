package xyz.wongs.weathertop.dependency.injection.setter;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import xyz.wongs.weathertop.dependency.injection.BookHandler;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * XML配置方式注入
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName XmlInjectionBySetterDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/23 14:35
 * @Version 1.0.0
 */
public class XmlInjectionBySetterDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/dependency-setter-injection.xml";
        reader.loadBeanDefinitions(path);

        BookHandler bookHandler = beanFactory.getBean(BookHandler.class);
        System.out.println(bookHandler);
    }
}
