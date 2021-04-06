package xyz.wongs.weathertop.ioc.dependency.lookup.overview.container;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

import java.util.Map;

/**
 *  see {@link AnnotationConfigApplicationContext} 作为 容器
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BeanFactoryIocContainer
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/6 14:33
 * @Version 1.0.0
 */
public class AnnotationAsIocContainer {

    public static void main(String[] args) {
        // 1、创建BeanFactory 容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 2、当前类作为配置类（Configuration Class）
        context.register(AnnotationAsIocContainer.class);
        // 3、启用上下文
        context.refresh();
        // 4、依赖查找集合对象
        lookupCollectionByType(context);
        // 5、关闭应用上下文
        context.close();
    }

    @Bean
    public Book book() {
        Book book = new Book();
        book.setId(2L);
        book.setName("Thinking In Java");
        return  book;
    }

    /** 集合查找
     * @Description
     * @param beanFactory
     * @return void
     * @throws
     * @date 21/4/2 14:47
     */
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String, Book> map = listableBeanFactory.getBeansOfType(Book.class);
            System.out.println("【集合方式查找】 "+ map);
        }
    }
}




