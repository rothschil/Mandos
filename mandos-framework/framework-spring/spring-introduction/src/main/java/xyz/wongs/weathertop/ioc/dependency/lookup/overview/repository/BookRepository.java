package xyz.wongs.weathertop.ioc.dependency.lookup.overview.repository;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

import java.util.Collection;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BookRepository
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/2 15:34
 * @Version 1.0.0
 */
public class BookRepository {

    private Collection<Book> users;

    private BeanFactory beanFactory;

    private ObjectFactory<ApplicationContext> objectFactory;

    public Collection<Book> getUsers() {
        return users;
    }

    public void setUsers(Collection<Book> users) {
        this.users = users;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ObjectFactory<ApplicationContext> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
        this.objectFactory = objectFactory;
    }
}
