package xyz.wongs.weathertop.dependency.injection.setter;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import xyz.wongs.weathertop.dependency.injection.BookHandler;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * API元信息配置
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName ApiBeanDependencyInjectionDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/23 14:35
 * @Version 1.0.0
 */
public class ApiBeanDependencyInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        BeanDefinition beanDefinition = createBeanDefinition();
        context.registerBeanDefinition("bookHandler",beanDefinition);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
        String path = "classpath:/META-INF/dependency-lookup.xml";
        reader.loadBeanDefinitions(path);

        context.refresh();
        BookHandler bookHandler = context.getBean(BookHandler.class);
        System.out.println(bookHandler);
        context.close();
    }

    public static BeanDefinition createBeanDefinition(){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(BookHandler.class);
        builder.addPropertyReference("book","bookSupport");
        return builder.getBeanDefinition();
    }
}
