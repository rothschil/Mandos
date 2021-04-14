package xyz.wongs.weathertop.bean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

import java.util.Map;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName RegisterBean
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/6 17:06
 * @Version 1.0.0
 */
@Import(value =RegisterBean.ConfigureBean.class)
public class RegisterBean {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RegisterBean.class);
        // 二、API配置方式
        // 2.1 非命名方式
        registerBookBean(context);
        // 2.2 命名方式
        registerBookBean("SamBook",context);
        context.refresh();
        // 一、注解方式配置Bean
        System.out.println("【ConfigureBean Beans 有】"+ context.getBeansOfType(ConfigureBean.class));

        Map<String ,Book> bookMap = context.getBeansOfType(Book.class);

        System.out.print("【Book Beans 有】"+ bookMap.size()  +" \n 【内容有】"+ context.getBeansOfType(Book.class));

        context.close();
    }

    public static void registerBookBean(String beanName, BeanDefinitionRegistry registry){
        BeanDefinitionBuilder builder =BeanDefinitionBuilder.genericBeanDefinition(Book.class);
        builder.addPropertyValue("id",34);
        builder.addPropertyValue("name","Thinking In Java");
        if(StringUtils.hasText(beanName)){
            registry.registerBeanDefinition(beanName,builder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(),registry);
        }
    }

    public static void registerBookBean(BeanDefinitionRegistry registry){
        registerBookBean(null,registry);
    }


    @Component
    static class ConfigureBean{

        /** 通过 @Bean 方式注册Bean
         * @Description
         * @return
         * @throws
         * @date 21/4/6 17:07
        */
        @Bean
        public Book book() {
            Book book = new Book();
            book.setId(2L);
            book.setName("Thinking In Java");
            return  book;
        }
    }
}
