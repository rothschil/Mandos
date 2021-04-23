package xyz.wongs.weathertop.dependency.injection;

import org.springframework.context.LifecycleProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName BuiltInDependencyLookup
 * @Description 
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/22 14:41
 * @Version 1.0.0
*/
public class BuiltInDependencyLookup {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();
        //disableSystemEnvironment(context);
        //disableJavaSystemEnvironment(context);
        disableEnvironment(context);


        context.close();
    }

    /** ApplicationEventMulticaster
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 15:07
     */
    private static void disableApplicationEventMulticaster(AbstractApplicationContext context) {
        ApplicationEventMulticaster avm = (ApplicationEventMulticaster)context.getBean("applicationEventMulticaster");

    }


    /** LifecycleProcessor
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 15:07
     */
    private static void disableLifecycleProcessor(AbstractApplicationContext context) {
        LifecycleProcessor lifecycleProcessor = (LifecycleProcessor)context.getBean("lifecycleProcessor");
    }

    /** 外部化配置以及Profiles
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 15:07
     */
    private static void disableEnvironment(AbstractApplicationContext context) {
        Environment environment = (Environment)context.getBean("environment");
        String[] ens = environment.getActiveProfiles();
        for (String en : ens) {
            System.out.println(en);
        }
    }

    /** Java环境变量
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 14:54
     */
    private static void disableJavaSystemEnvironment(AbstractApplicationContext context) {
        Properties properties = (Properties)context.getBean("systemProperties");
        Enumeration enumeration = properties.propertyNames();
        while(enumeration.hasMoreElements()){
            Object key = enumeration.nextElement();
            System.out.println("Key = "+ key +" ;Value= "+properties.get(key));
        }
    }

    /** 操作系统环境变量
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 14:54
     */
    private static void disableSystemEnvironment(AbstractApplicationContext context) {
        Map map = (Map)context.getBean("systemEnvironment");
        map.forEach((key,value)->System.out.println("Key= "+key+"\t"+" ;Value= "+map.get(key)));
    }


}
