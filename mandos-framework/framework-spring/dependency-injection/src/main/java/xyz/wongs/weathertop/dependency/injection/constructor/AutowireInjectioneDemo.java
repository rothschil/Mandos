package xyz.wongs.weathertop.dependency.injection.constructor;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import xyz.wongs.weathertop.dependency.injection.BookHandler;

/**
 * Autowiring 构造器
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName AutowireInjectioneDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/23 14:35
 * @Version 1.0.0
 */
public class AutowireInjectioneDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/autowire-dependency-constructor2-injection.xml";
        reader.loadBeanDefinitions(path);

        BookHandler bookHandler = beanFactory.getBean(BookHandler.class);
        System.out.println(bookHandler);
    }
}
