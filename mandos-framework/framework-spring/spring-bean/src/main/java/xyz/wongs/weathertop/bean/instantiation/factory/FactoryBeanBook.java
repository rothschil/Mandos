package xyz.wongs.weathertop.bean.instantiation.factory;

import org.springframework.beans.factory.FactoryBean;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName FactoryBeanBook
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/7 10:36
 * @Version 1.0.0
 */
public class FactoryBeanBook implements FactoryBean<Book> {

    @Override
    public Book getObject() throws Exception {
        return Book.initBean();
    }

    @Override
    public Class<Book> getObjectType() {
        return Book.class;
    }
}
