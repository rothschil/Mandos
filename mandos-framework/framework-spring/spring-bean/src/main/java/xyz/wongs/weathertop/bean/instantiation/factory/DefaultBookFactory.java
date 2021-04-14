package xyz.wongs.weathertop.bean.instantiation.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName DefaultBookFactory
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/7 10:25
 * @Version 1.0.0
 */
public class DefaultBookFactory implements BookFactory, InitializingBean, DisposableBean {

    private static Log LOG  = LogFactory.getLog(DefaultBookFactory.class);

    @PostConstruct
    public void postConstruct() throws Exception {
        System.out.println("【postConstruct】 初始化中");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【afterPropertiesSet】 初始化中");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("【finalize】 初始化中");
    }

    public void initBookFactory() {
        System.out.println("【initBookFactory】 自定义方法初始化");
    }

    @PreDestroy
    protected void preDestroy(){
        System.out.println("【preDestroy】 销毁中");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("【destroy】 销毁");
    }

    public void doDestroy() throws Exception {
        System.out.println("【doDestroy】 自定义方法销毁");
    }


}
