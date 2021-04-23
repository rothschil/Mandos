package xyz.wongs.weathertop.dependency.injection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @ClassName NoUniqueBeanDefinitionExceptionDemo
 * @Description 
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/22 14:41
 * @Version 1.0.0
*/
public class BeanDefinitionExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Pojo.class);
        context.registerBeanDefinition("errBean",builder.getBeanDefinition());
        context.refresh();

        context.close();
    }

    static class Pojo implements InitializingBean{

        @PostConstruct
        public void init() throws Throwable{
            throw new Exception("【init】 For purposes...");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            throw  new Exception("【afterPropertiesSet】 For purposes...");
        }
    }

}
