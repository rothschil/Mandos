package xyz.wongs.weathertop.bean.instantiation;

import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import xyz.wongs.weathertop.bean.instantiation.factory.BookFactory;
import xyz.wongs.weathertop.bean.instantiation.factory.DefaultBookFactory;

/**
 *  外部单体对象在容器中的注册
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName SingletionBeanRegistry
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/14 14:31
 * @Version 1.0.0
 */
public class SingletionBeanRegistry {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();

        BookFactory booKactory = new DefaultBookFactory();
        SingletonBeanRegistry factory = context.getBeanFactory();
        factory.registerSingleton("singletonBookFactory",booKactory);
        BookFactory booKactoryByName =  context.getBean("singletonBookFactory",BookFactory.class);

        System.out.println("【】"+(booKactory==booKactoryByName));

        context.close();
    }
}
