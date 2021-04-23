package xyz.wongs.weathertop.dependency.injection;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName NoUniqueBeanDefinitionExceptionDemo
 * @Description 
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/22 14:41
 * @Version 1.0.0
*/
public class NoUniqueBeanDefinitionExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(NoUniqueBeanDefinitionExceptionDemo.class);
        context.refresh();

        try {
            context.getBean(String.class);
        } catch (NoUniqueBeanDefinitionException e) {
            System.err.printf("当前Bean 数量有 %d 个，名称是 %s，错误信息是 %s \n",e.getNumberOfBeansFound(),String.class.getName(),e.getMessage());
        }
        context.close();
    }


    @Bean
    public String bean1(){
        return "one";
    }

    @Bean
    public String bean2(){
        return "two";
    }

    @Bean
    public String bean3(){
        return "three";
    }


}
