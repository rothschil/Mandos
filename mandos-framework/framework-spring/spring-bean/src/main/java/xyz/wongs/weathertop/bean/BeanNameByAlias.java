package xyz.wongs.weathertop.bean;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BeanNameByAlias
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/6 16:32
 * @Version 1.0.0
 */
public class BeanNameByAlias {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-name.xml");

        Book bookAlias = (Book)context.getBean("samBook");
        Book bookByName = (Book)context.getBean("book");

        System.out.println("【】" + (bookAlias==bookByName));

    }
}
