package xyz.wongs.weathertop.java.base.reflect.attr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import xyz.wongs.weathertop.java.base.reflect.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName AttrReflect
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/27 9:51
 * @Version 1.0.0
 */
public class ReflectDemo {

    private static Log log = LogFactory.getLog(ReflectDemo.class);
    public static void main(String[] args) throws Exception{
        Person person = new Person(1L,"张三","暂无");
        Class pClass = person.getClass();
        //attr(persion);
//        method(person);
        constructors(person);
    }

    private static void constructors(Person person){
        Class pClass = person.getClass();
        Constructor[] constructors =pClass.getDeclaredConstructors();
        for (Constructor  constructor: constructors) {
            Class<?>[] clazz =constructor.getParameterTypes();
            int counts =constructor.getParameterCount();
            Person personCon = null;
            while(null==personCon){
                try {
                    switch (counts) {
                        case 0: personCon = (Person)constructor.newInstance(); break;
                        case 3:
                            Object[] parameters = new Object[] {  12013L, "李四","百威" };
                            personCon = (Person)constructor.newInstance(12013L,"李四","百威");
                            break;
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(personCon.toString());
        }
    }

    private static void method(Person person){
        Class pClass = person.getClass();
        Method[] methods = pClass.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if(methodName.startsWith("get")){
                try {
                    Object obj = method.invoke(person);
                    log.error(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * @Description
     * @param person
     * @return void
     * @throws
     * @date 2021/5/27 10:38
     */
    private static void attr(Person person){
        Class pClass = person.getClass();
        Field[] fields = pClass.getDeclaredFields();
        for (Field field : fields) {
            Class fieldType = field.getType();
            log.info("成员名称为：" + field.getName());
            log.info("成员类型为：" + fieldType.getName());
            boolean flag = true;
            while(flag){
                try {
                    flag = false;
                    if(fieldType.equals(Long.class)){
                        System.out.println("【成员变量】 "+Long.parseLong(field.get(person).toString()));
                    } else {
                        System.out.println("【成员变量】 "+field.get(person));
                    }
                } catch (IllegalAccessException e) {
                    flag = true;
                    field.setAccessible(true);
                }
            }
        }
    }
}
