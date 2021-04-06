package xyz.wongs.weathertop.ioc.dependency.lookup.overview.container;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 *  see {@link org.springframework.beans.factory.BeanFactory} 作为 容器
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BeanFactoryIocContainer
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/6 14:33
 * @Version 1.0.0
 */
public class BeanFactoryAsIocContainer {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        int cout = reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup.xml");
        System.out.println("【Bean数量】 = "+ cout);
    }
}
