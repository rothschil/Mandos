package xyz.wongs.weathertop.ioc.index;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BeanInfoDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/1 16:51
 * @Version 1.0.0
 */
public class BeanInfoDemo {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(DlsInfo.class,Object.class);
        DlsInfo dlsInfo = new DlsInfo();
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(propertyDescriptor -> {
//            System.out.println(propertyDescriptor.toString());

            Class<?> propertyType = propertyDescriptor.getPropertyType();
            String propertyName = propertyDescriptor.getName();
            if("age".equalsIgnoreCase(propertyName)){
                propertyDescriptor.setPropertyEditorClass(ControPropertyEditor.class);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                try {
                    writeMethod.invoke(dlsInfo,"34");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
//                propertyDescriptor.createPropertyEditor(dlsInfo);
            }
        });

        System.out.println(dlsInfo.getAge());
    }

    class ControPropertyEditor extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }
}
