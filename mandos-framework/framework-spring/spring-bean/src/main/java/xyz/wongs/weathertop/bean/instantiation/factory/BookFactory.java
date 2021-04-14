package xyz.wongs.weathertop.bean.instantiation.factory;

import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BookFactory
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/7 10:24
 * @Version 1.0.0
 */
public interface BookFactory {

    default Book initBean(){
        return Book.initBean();
    }
}
