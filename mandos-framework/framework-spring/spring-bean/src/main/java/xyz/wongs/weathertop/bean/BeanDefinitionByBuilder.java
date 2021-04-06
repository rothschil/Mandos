package xyz.wongs.weathertop.bean;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BeanDefinitionByBuilder
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/6 15:43
 * @Version 1.0.0
 */
public class BeanDefinitionByBuilder {

    public static void main(String[] args) {
        // 1.使用 BeanDefinitionBuilder 提供的构造器
        BeanDefinitionBuilder builder =BeanDefinitionBuilder.genericBeanDefinition(Book.class);
        // 1.1 通过属性设置
        builder.addPropertyValue("id",34);
        builder.addPropertyValue("name","Thinking In Java");

        // 1.2 BeanDefinition 实例
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        // BeanDefinition 并非 Bean 终态，可以自定义修改

        // 2. 通过 AbstractBeanDefinition 以及派生类
        GenericBeanDefinition definition = new GenericBeanDefinition();
        // 2.1 设置Bean Class
        definition.setBeanClass(Book.class);

        // 2.2 通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("id",34).add("name","Thinking In Java");
        definition.setPropertyValues(values);

    }
}
