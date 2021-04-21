package xyz.wongs.weathertop.dependency.loopup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName HierarchicalDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/14 16:21
 * @Version 1.0.0
 */
public class HierarchicalDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(HierarchicalDemo.class);
        // 1、HierarchicalFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //System.out.println("【当前 Bean 的 ParentBeanFactory 】 "+  beanFactory.getParentBeanFactory());
        HierarchicalBeanFactory paraentBeanFactory = initBeanFactory();
        beanFactory.setParentBeanFactory(paraentBeanFactory);
        //System.out.println("【当前 Bean 的 ParentBeanFactory 】 "+  beanFactory.getParentBeanFactory());
        displayContainsLocalBean(beanFactory,"book");
        displayContainsLocalBean(paraentBeanFactory,"book");
        displayContainsBean(paraentBeanFactory,"book");
        displayContainsBean(paraentBeanFactory,"book");
        context.refresh();
        context.close();
    }


    public static void displayContainsBean(HierarchicalBeanFactory beanFactory,String beanName){
        System.out.printf("【当前 BeanFactory[%s] 是否包含 Bean[name : %s] 】 : %s\n",beanFactory,beanName,containsBean(beanFactory,beanName));
    }

    public static boolean containsBean(HierarchicalBeanFactory beanFactory,String beanName){
        BeanFactory paranetBeanFactory = beanFactory.getParentBeanFactory();
        if(paranetBeanFactory instanceof HierarchicalBeanFactory){
            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(paranetBeanFactory);
            if(containsBean(parentHierarchicalBeanFactory,beanName));
        }
        return beanFactory.containsLocalBean(beanName);
    }


    public static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory,String beanName){
        System.out.printf("【当前 BeanFactory[%s] 是否包含 Local Bean[name : %s] 】 : %s\n",beanFactory,beanName,beanFactory.containsLocalBean(beanName));
    }

    private static DefaultListableBeanFactory initBeanFactory(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup.xml");
        return beanFactory;
    }

}
