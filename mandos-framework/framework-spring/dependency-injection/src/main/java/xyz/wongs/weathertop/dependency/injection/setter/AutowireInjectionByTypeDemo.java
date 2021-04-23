package xyz.wongs.weathertop.dependency.injection.setter;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import xyz.wongs.weathertop.dependency.injection.BookHandler;

/**
 * Autowiring
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName AutowireInjectionByTypeDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/23 14:35
 * @Version 1.0.0
 */
public class AutowireInjectionByTypeDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/autowire-by-type-dependency-setter-injection.xml";
        reader.loadBeanDefinitions(path);

        BookHandler bookHandler = beanFactory.getBean(BookHandler.class);
        System.out.println(bookHandler);
    }
}
