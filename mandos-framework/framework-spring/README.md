<!-- TOC -->

- [~~~log](#log)
- [ObjectProvider](#objectprovider)
- [ListableBeanFactory](#listablebeanfactory)
    - [4.6. 内建可查找的依赖](#46-内建可查找的依赖)
    - [4.7. 依赖查找中的经典异常](#47-依赖查找中的经典异常)
  - [5. 依赖注入](#5-依赖注入)
    - [5.1. 依赖注入模式和类型](#51-依赖注入模式和类型)
    - [5.2. 自动绑定](#52-自动绑定)
      - [5.2.1. 背景](#521-背景)
      - [5.2.2. 模式](#522-模式)
      - [5.2.3. 限制和不足](#523-限制和不足)
    - [5.3. 依赖注入方式](#53-依赖注入方式)
      - [5.3.1. setter注入](#531-setter注入)
        - [5.3.1.1. XML资源配置注入](#5311-xml资源配置注入)
        - [5.3.1.2. Java注解配置注入](#5312-java注解配置注入)
        - [5.3.1.3. API配置元信息](#5313-api配置元信息)
        - [5.3.1.4. 按名称和按类型自动注入](#5314-按名称和按类型自动注入)
      - [5.3.2. 构造器注入](#532-构造器注入)
      - [5.3.3. 字段注入](#533-字段注入)
      - [5.3.4. 方法注入](#534-方法注入)
      - [5.3.5. 接口回调注入](#535-接口回调注入)
    - [5.4. 依赖注入类型选择](#54-依赖注入类型选择)
    - [5.5. 基础类型注入选择](#55-基础类型注入选择)
    - [5.6. 集合类型注入选择](#56-集合类型注入选择)
    - [5.7. 限定注入](#57-限定注入)
    - [5.8. 延迟依赖注入](#58-延迟依赖注入)
    - [5.9. 依赖处理过程](#59-依赖处理过程)
    - [5.10. Autowired注入](#510-autowired注入)
    - [5.11. Inject注入](#511-inject注入)
    - [5.12. Java通用注解注入原理](#512-java通用注解注入原理)
    - [5.13. 自定义依赖注解注入原理](#513-自定义依赖注解注入原理)
  - [6. Bean作用域（Bean Scopes）](#6-bean作用域bean-scopes)
  - [7. Bean生命周期（Bean Lifecycle）](#7-bean生命周期bean-lifecycle)
  - [8. 配置元信息（Configuration Metadata）](#8-配置元信息configuration-metadata)
  - [9. 资源管理（Resources）](#9-资源管理resources)
  - [10. Sping国际化](#10-sping国际化)
  - [11. 校验（Validation）](#11-校验validation)
  - [12. 数据绑定（Data Binding）](#12-数据绑定data-binding)
  - [13. 类型转换（Type Conversion）](#13-类型转换type-conversion)
  - [14. 泛型处理（Generic Resolution）](#14-泛型处理generic-resolution)
  - [15. Spring事件（Events）](#15-spring事件events)
  - [16. Annotations](#16-annotations)
  - [17. Environment抽象](#17-environment抽象)
  - [18. 应用上下文生命周期（Container Lifecycle）](#18-应用上下文生命周期container-lifecycle)

<!-- /TOC -->

## 1. 总览

### 1.1. 编程模型

- 面向对象编程：基于契约接口实现

- 面向元编程：配置元信息、注解、属性配置

- 面向切面编程：动态代理、字节码提升

- 面向模块编程

- 面向函数编程：Lambda、Reactive（异步非阻塞）

### 1.2. 環境準備

- Spring版本：[5.2.2.RELEASE](https://codeload.github.com/spring-projects/spring-framework/zip/refs/tags/v5.2.2.RELEASE)
- IDEA：2020+
- Maven：3.6+
- JDK：1.8+

### 1.3. 特性總覽

![Spring官網](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210401151821.png)

### 1.4. 版本特性

![Spring與JDK](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210401153124.png)

### 1.5. 模塊化設計

![20210401153715](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210401153715.png)

![Spring在Github](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210401153824.png)

## 2. Spring Ioc基本概述

[Wiki](https://en.wikipedia.org/wiki/Inversion_of_control)

![20210401154939](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210401154939.png)

### 2.1. 概要说明

#### 2.1.1. 实施技术

![20210401155227](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210401155227.png)

#### 2.1.2. 輕量級容器鑒定標準

- 容器可以管理應用的代碼
- 容器可以快速啓動
- 容器本身對外界的依賴盡可能的小
- 容器輕量内存占用和最小API
- 容器可以管控

#### 2.1.3. 好處

- 釋放單體應用
- 最大化代碼復用
- 更大程度的面嚮對象
- 更大程度的產品化
- 可測試性

### 2.2. 依賴注入和依賴查找比较

![20210402093528](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210402093528.png)

#### 2.2.1. Ioc依賴查找（Dependency Loopup）

![20210402105648](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210402105648.png)

##### 2.2.1.1. 通過名稱查找

##### 2.2.1.2. 通過類型查找

###### 2.2.1.2.1. 單個Bean對象

###### 2.2.1.2.2. 集合Bean對象

###### 2.2.1.2.3. 按照注解查找

#### 2.2.2. Ioc依賴注入（Dependency Injection）

##### 2.2.2.1. 手動方式

##### 2.2.2.2. autowire方式

#### 2.2.3. Ioc依賴来源（Dependency Sources）

![20210402162032](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210402162032.png)

![20210402163124](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210402163124.png)

#### 2.2.4. 配置元信息

#### 2.2.5. IOC容器

- BeanFactory

- ApplicationContext

#### 2.2.6. 容器上下文

#### 2.2.7. 使用Spring IOC容器

- BeanFactory容器

- AnnotationConfigApplicationContext 容器

#### 2.2.8. Spring IOC容器生命周期

- 启动

- 运行

- 停止

## 3. Bean基础

### 3.1. 定义Bean

`Spring Framework` 中定义 `Bean` 的配置元信息接口，包含了 `Bean` 的类名、`Bean`行为配置（作用域、自动绑定模式、生命周期回调）、其他 `Bean` 引用（）、配置设置（`Bean`属性）

### 3.2. BeanDefinition元信息

![元信息](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210406152637.png)

#### 3.2.1. BeanDefinitionBuilder

#### 3.2.2. AbstractBeanDefinition

### 3.3. 命名Spring Bean

- Bean 名称

每个Bean 拥有一个或多个标识符（identifiers），这些标识符在Bean 所在的容器必须是唯一的。通常，一个Bean 仅有一个标识符，如果需要额外的，可考虑使用别名（Alias）来扩充。

在基于XML 的配置元信息中，开发人员可用id 或者name 属性来规定Bean 的标识符。通常
Bean 的标识符由字母组成，允许出现特殊字符。如果要想引入Bean 的别名的话，可在
name 属性使用半角逗号（“,”）或分号（“;”) 来间隔。

Bean 的id 或name 属性并非必须制定，如果留空的话，容器会为Bean 自动生成一个唯一的名称。Bean 的命名尽管没有限制，不过官方建议采用驼峰的方式，更符合Java 的命名约定。

- Bean名称生成器（BeanNameGenerator）

- 由Spring Framework 2.0.3 引入，框架內建两种实现：DefaultBeanNameGenerator：默认通用BeanNameGenerator 实现

- AnnotationBeanNameGenerator：基于注解扫描的BeanNameGenerator 实现，起始于
Spring Framework 2.5

### 3.4. Spring Bean 别名

### 3.5. 注册Spring Bean

- XML配置

- 注解配置: @Bean、@Compact、@Import

- Java API配置信息
    - 命名方式

~~~
BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)
~~~

    - 非命名方式

~~~
BeanDefinitionReaderUtils#registerWithGeneratedName(AbstractBeanDefinition,BeanDefinitionRegistry)
~~~
    - 配置类方式

~~~
AnnotatedBeanDefinitionReader#register(Class...)
~~~

### 3.6. 实例化Spring Bean

#### 3.6.1. 常规方式

- 通过构造器（配置元信息：XML、Java 注解和Java API ）
- 通过静态工厂方法（配置元信息：XML 和Java API ）

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">

    <import resource="dependency-injection.xml"/>

    <bean id="instace-static-method" class="xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book" factory-method="initBean"/>

</beans>
~~~

~~~java
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation.xml");

// 1、靜態方法方式實例化Bean
Book staticBook = context.getBean("instace-static-method", Book.class);

System.out.println("【靜態方法方式實例化Bean】"+staticBook+" ;Hash = "+staticBook.hashCode());
~~~

- 通过Bean 工厂方法（配置元信息：XML和Java API ）

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">

    <import resource="dependency-injection.xml"/>

    <bean id="instace-static-method" class="xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book" factory-method="initBean"/>

    <bean id="instace-bean-factory" factory-bean="bookFactory" factory-method="initBean"/>

    <bean id="bookFactory" class="xyz.wongs.weathertop.bean.instantiation.factory.DefaultBookFactory"/>

</beans>
~~~

~~~java
public interface BookFactory {

    default Book initBean(){
        return Book.initBean();
    }
}


public class DefaultBookFactory implements BookFactory {

}

public class BeanInstantiationDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation.xml");

        // 1、靜態方法方式實例化Bean
        Book staticBook = context.getBean("instace-static-method", Book.class);


        // 2、工廠方法方式實例化Bean
        Book beanFactoryBook = context.getBean("instace-bean-factory", Book.class);

        System.out.println("【靜態方法方式實例化Bean】"+staticBook+" ;Hash = "+staticBook.hashCode());

        System.out.println("【工廠方法方式實例化Bean】"+beanFactoryBook+" ;Hash = "+beanFactoryBook.hashCode());

        System.out.println(staticBook==beanFactoryBook);
    }

}

~~~

- 通过FactoryBean（配置元信息：XML、Java 注解和Java API ）

~~~java
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

public class BeanInstantiationDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation.xml");

        // 1、靜態方法方式實例化Bean
        Book staticBook = context.getBean("instace-static-method", Book.class);


        // 2、工廠方法方式實例化Bean
        Book beanFactoryBook = context.getBean("instace-bean-factory", Book.class);

        // 3、通过FactoryBean方式實例化Bean
        Book factoryBeanBook = context.getBean("instace-factory-bean", Book.class);

        System.out.println("【靜態方法方式實例化Bean】"+staticBook+" ;Hash = "+staticBook.hashCode());

        System.out.println("【工廠方法方式實例化Bean】"+beanFactoryBook+" ;Hash = "+beanFactoryBook.hashCode());

        System.out.println("【通过FactoryBean方式實例化Bean】"+factoryBeanBook+" ;Hash = "+factoryBeanBook.hashCode());

        System.out.println(staticBook==beanFactoryBook);
        System.out.println(beanFactoryBook==factoryBeanBook);
    }

}
~~~

~~~xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">

    <import resource="dependency-injection.xml"/>

    <bean id="instace-static-method" class="xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book" factory-method="initBean"/>

    <bean id="instace-bean-factory" factory-bean="bookFactory" factory-method="initBean"/>

    <bean id="bookFactory" class="xyz.wongs.weathertop.bean.instantiation.factory.DefaultBookFactory"/>

    <bean id="instace-factory-bean" class="xyz.wongs.weathertop.bean.instantiation.factory.FactoryBeanBook"/>

</beans>
~~~~

#### 3.6.2. 特殊方式

- 通过ServiceLoaderFactoryBean（配置元信息：XML、Java 注解和Java API ）

需要在路径 `META-INF/services/` 下创建文件

![20210407143822](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210407143822.png)

![20210407145729](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210407145729.png)

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">

    <bean id="specialLoaderBookFactoryBean" class="org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean">
        <property name="serviceType" value="xyz.wongs.weathertop.bean.instantiation.factory.BookFactory" />
    </bean>
</beans>
~~~

~~~java
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation.xml");
        ServiceLoader<BookFactory> loader = context.getBean("specialLoaderBookFactoryBean",ServiceLoader.class);
        displayServiceLoader(loader);
//        demoServiceLoader();
    }

    public static void demoServiceLoader(){
        ServiceLoader<BookFactory> loader = ServiceLoader.load(BookFactory.class,Thread.currentThread().getContextClassLoader());
        displayServiceLoader(loader);
    }

    public static void displayServiceLoader(ServiceLoader<BookFactory> loader){
        Iterator<BookFactory> iterator = loader.iterator();
        while (iterator.hasNext()){
            BookFactory factory = iterator.next();
            System.out.println(factory.initBean());
        }
    }
}

~~~

- 通过AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)

~~~java
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation.xml");
        // 通过 ApplicationContext 返回 AutowireCapableBeanFactory
        AutowireCapableBeanFactory beanFactory  = context.getAutowireCapableBeanFactory();

        BookFactory factory = beanFactory.createBean(DefaultBookFactory.class);
        System.out.println(factory.initBean());

    }

}
~~~

- 通过BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)

### 3.7. 初始化Spring Bean

![20210414095730](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210414095730.png)

~~~java
public class DefaultBookFactory implements BookFactory, InitializingBean {

    private static Log LOG  = LogFactory.getLog(DefaultBookFactory.class);

    @PostConstruct
    public void postConstruct() throws Exception {
        LOG.info("【postConstruct】 初始化中");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("【afterPropertiesSet】 初始化中");
    }

    @Override
    protected void finalize() throws Throwable {
        LOG.info("【finalize】 初始化中");
    }

    public void initBookFactory() {
        LOG.info("【initBookFactory】 自定义方法初始化");
    }
}


@Configuration
public class InitializingBeanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(InitializingBeanDemo.class);
        context.refresh();
        System.out.println("启动上下文");
        BookFactory factory  = context.getBean(BookFactory.class);
        System.out.println("准备关闭上下文");
        context.close();
        System.out.println("关闭上下文");
    }

    @Bean(initMethod = "initBookFactory")
    public BookFactory bookFactory(){
        return new DefaultBookFactory();
    }
}


~~~

### 3.8. 延迟初始化Spring Bean

`@Lazy` 默认是 延迟加载，所以无需延迟，则不用加这个注解

~~~java

@Configuration
public class InitializingBeanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(InitializingBeanDemo.class);
        context.refresh();
        System.out.println("启动上下文");
        BookFactory factory  = context.getBean(BookFactory.class);
        System.out.println(factory);
        System.out.println("准备关闭上下文");
        context.close();
        System.out.println("关闭上下文");
    }

    @Bean(initMethod = "initBookFactory")
    @Lazy
    public BookFactory bookFactory(){
        return new DefaultBookFactory();
    }
}
~~~

![非延迟初始化](https://abram.oss-cn-shanghai.aliyuncs.com/blog/mandos/spring/20210414111708.png)

![延迟初始化](https://abram.oss-cn-shanghai.aliyuncs.com/blog/mandos/spring/20210414111636.png)

~~~java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
context.register(InitializingBeanDemo.class);
context.refresh();
~~~

题外话，`context.refresh()` 中 方法 `finishBeanFactoryInitialization`，只处理非延迟初始化的 `Bean`，对于容器内置 Bean以及需要延迟初始化的则不在此处初始化。

![20210414100804](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210414100804.png)

![20210414100851](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210414100851.png)

这一点需要注意。

### 3.9. 销毁Spring Bean

### 3.10. 垃圾回收Spring Bean

## 4. 依赖查找

### 4.1. 单一类型查找

![20210414155115](https://abram.oss-cn-shanghai.aliyuncs.com/blog/mandos/spring/20210414155115.png)

### 4.2. 集合类型查找

![20210414155048](https://abram.oss-cn-shanghai.aliyuncs.com/blog/mandos/spring/20210414155048.png)

### 4.3. 层次性依赖查找

![20210414155837](https://abram.oss-cn-shanghai.aliyuncs.com/blog/mandos/spring/20210414155837.png)

![类结构图](https://abram.oss-cn-shanghai.aliyuncs.com/blog/mandos/spring/20210414161309.png)

### 4.4. 延迟依赖查找

![20210421155958](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210421155958.png)


ObjectProvider 

### 4.5. 安全依赖查找

![20210422105512](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210422105512.png)


~~~java
public class TypeSafetyDependencyLookup {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ObjectProviderDemo.class);
        context.refresh();
        // BeanFactory#getBean
        disableBeanFactoryGetBean(context);
        // ObjectFactory#getObject
        disableObjectFactoryGetBean(context);
        // ObjectProvider#getIfAvailable
        disableObjectProviderGetIfAvailable(context);
        // ListableBeanFactory#getBeansOfType
        disableListableBeanFactoryGetBeansOfType(context);
        // ObjectProvider#stream
        disableObjectProviderByStream(context);
        context.close();
    }


    /**
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 11:15
     */
    private static void disableObjectProviderByStream(AnnotationConfigApplicationContext context) {
        ObjectProvider<Book> objectProvider = context.getBeanProvider(Book.class);
        printBeanException("ObjectProvider",()->{
            objectProvider.stream().forEach(System.out::println);
        });
    }

    /**
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 11:15
     */
    private static void disableListableBeanFactoryGetBeansOfType(AnnotationConfigApplicationContext context) {
        ListableBeanFactory beanFactory = context.getBeanFactory();
        printBeanException("ListableBeanFactory",()->{
            beanFactory.getBeansOfType(Book.class);
        });
    }

    /**
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 11:15
     */
    private static void disableObjectProviderGetIfAvailable(AnnotationConfigApplicationContext context) {
        ObjectProvider<Book> objectProvider = context.getBeanProvider(Book.class);
        printBeanException("ObjectProvider",()->{
            objectProvider.getIfAvailable();
        });
    }

    /**
     * @Description
     * @param context
     * @return void
     * @throws
     * @date 2021/4/22 11:15
     */
    private static void disableObjectFactoryGetBean(AnnotationConfigApplicationContext context) {
        ObjectFactory<Book> bookObjectFactory = context.getBeanProvider(Book.class);
        printBeanException("ObjectFactory",()->{
            bookObjectFactory.getObject();
        });
    }

    /**
     * @Description
     * @param beanFactory
     * @return void
     * @throws
     * @date 2021/4/22 11:14
     */
    private static void disableBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeanException("BeanFactory",()->{
            Book book = beanFactory.getBean(Book.class);
        });
    }

    public static void printBeanException(String resource ,Runnable runnable){
        try {
            System.err.println("===============================");
            System.err.println(resource);
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

~~~

~~~log
===============================
BeanFactory
org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:351)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:342)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1126)
	at xyz.wongs.weathertop.dependency.injection.TypeSafetyDependencyLookup.lambda$disableBeanFactoryGetBean$4(TypeSafetyDependencyLookup.java:103)
	at xyz.wongs.weathertop.dependency.injection.TypeSafetyDependencyLookup.printBeanException(TypeSafetyDependencyLookup.java:111)
	at xyz.wongs.weathertop.dependency.injection.TypeSafetyDependencyLookup.disableBeanFactoryGetBean(TypeSafetyDependencyLookup.java:102)
	at xyz.wongs.weathertop.dependency.injection.TypeSafetyDependencyLookup.main(TypeSafetyDependencyLookup.java:25)
===============================
ObjectFactory
org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory$1.getObject(DefaultListableBeanFactory.java:370)
	at xyz.wongs.weathertop.dependency.injection.TypeSafetyDependencyLookup.lambda$disableObjectFactoryGetBean$3(TypeSafetyDependencyLookup.java:90)
	at xyz.wongs.weathertop.dependency.injection.TypeSafetyDependencyLookup.printBeanException(TypeSafetyDependencyLookup.java:111)
	at xyz.wongs.weathertop.dependency.injection.TypeSafetyDependencyLookup.disableObjectFactoryGetBean(TypeSafetyDependencyLookup.java:89)
	at xyz.wongs.weathertop.dependency.injection.TypeSafetyDependencyLookup.main(TypeSafetyDependencyLookup.java:27)
===============================
ObjectProvider
===============================
ListableBeanFactory
===============================
ObjectProvider
~~~

### 4.6. 内建可查找的依赖

![AbstractApplicationContext内建可查找的依赖Bean](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210422143946.png)

![注解驱动Spring 应用上下文内建可查找的依赖](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210422151201.png)

![注解驱动Spring 应用上下文内建可查找的依赖](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210422151308.png)

### 4.7. 依赖查找中的经典异常

![BeansException 子类型](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210422153727.png)

## 5. 依赖注入

### 5.1. 依赖注入模式和类型


![20210423102844](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210423102844.png)


![20210423102904](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210423102904.png)

### 5.2. 自动绑定

#### 5.2.1. 背景

`Spring` 容器可以自动装配协作 `bean` 之间的关系，可以让 `Spring` 通过检查内容自动为您的 `bean` 解决协作者（其他 `bean` ） `ApplicationContext`。

优点：

- 自动装配可以大大减少指定属性或构造函数参数的需要
- 随着对象的发展，自动装配可以更新配置

#### 5.2.2. 模式

![20210423103301](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210423103301.png)


![AutowireCapableBeanFactory](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210423103354.png)

![Autowire](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210423103413.png)

#### 5.2.3. 限制和不足

[beans-factory-autowire官方说明](https://docs.spring.io/spring-framework/docs/5.2.2.RELEASE/spring-framework-reference/core.html#beans-factory-autowire)

- Explicit dependencies in `property` and `constructor-arg` settings always override autowiring. You cannot autowire simple properties such as primitives, `Strings`, and `Classes` (and arrays of such simple properties). This limitation is by-design.

- Autowiring is less exact than explicit wiring. Although, as noted in the earlier table, Spring is careful to avoid guessing in case of ambiguity that might have unexpected results. The relationships between your Spring-managed objects are no longer documented explicitly.

- Wiring information may not be available to tools that may generate documentation from a Spring container.

- Multiple bean definitions within the container may match the type specified by the setter method or constructor argument to be autowired. For arrays, collections, or `Map` instances, this is not necessarily a problem. However, for dependencies that expect a single value, this ambiguity is not arbitrarily resolved. If no unique bean definition is available, an exception is thrown.

### 5.3. 依赖注入方式

[beans-factory-collaborators](https://docs.spring.io/spring-framework/docs/5.2.2.RELEASE/spring-framework-reference/core.html#beans-factory-collaborators)

![依赖注入官方文档](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210423105106.png)

#### 5.3.1. setter注入

![setter方法注入](https://abram.oss-cn-shanghai.aliyuncs.com/blog/spring/20210423152310.png)

##### 5.3.1.1. XML资源配置注入

~~~java
public class BookHandler {

    private Book book;

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookHandler{" +
                "book= " + book.toString() +
                '}';
    }
}

public class XmlBeanDependencyInjectionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/dependency-setter-injection.xml";
        reader.loadBeanDefinitions(path);

        BookHandler bookHandler = (BookHandler)beanFactory.getBean("bookHandler");
        System.out.println(bookHandler);
    }
}

~~~

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="dependency-lookup.xml"/>

    <bean id="bookHandler" class="xyz.wongs.weathertop.dependency.injection.BookHandler">
        <property name="book" ref="bookSupport"/>
    </bean>
</beans>
~~~

##### 5.3.1.2. Java注解配置注入

~~~java
public class AnnotationBeanDependencyInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationBeanDependencyInjectionDemo.class);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
        String path = "classpath:/META-INF/dependency-lookup.xml";
        reader.loadBeanDefinitions(path);

        context.refresh();
        BookHandler bookHandler = context.getBean(BookHandler.class);
        System.out.println(bookHandler);
        context.close();
    }

    @Bean
    public BookHandler bookHandler(Book book){
        BookHandler handler = new BookHandler();
        handler.setBook(book);
        return handler;
    }
}

~~~

##### 5.3.1.3. API配置元信息

~~~java

/**
 * API元信息配置
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName ApiBeanDependencyInjectionDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/23 14:35
 * @Version 1.0.0
 */
public class ApiBeanDependencyInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        BeanDefinition beanDefinition = createBeanDefinition();
        context.registerBeanDefinition("bookHandler",beanDefinition);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
        String path = "classpath:/META-INF/dependency-lookup.xml";
        reader.loadBeanDefinitions(path);

        context.refresh();
        BookHandler bookHandler = context.getBean(BookHandler.class);
        System.out.println(bookHandler);
        context.close();
    }

    public static BeanDefinition createBeanDefinition(){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(BookHandler.class);
        builder.addPropertyReference("book","bookSupport");
        return builder.getBeanDefinition();
    }
}

~~~

##### 5.3.1.4. 按名称和按类型自动注入


#### 5.3.2. 构造器注入

~~~java
public class XmlInjectionByConstructorDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/dependency-counstructor-injection.xml";
        reader.loadBeanDefinitions(path);

        BookHandler bookHandler = beanFactory.getBean(BookHandler.class);
        System.out.println(bookHandler);
    }
}

~~~

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="dependency-lookup.xml"/>
    <bean class="xyz.wongs.weathertop.dependency.injection.BookHandler">
        <constructor-arg name="book" ref="bookSupport"/>
    </bean>
</beans>
~~~


#### 5.3.3. 字段注入

- autowired
- resource 
- inject

#### 5.3.4. 方法注入

#### 5.3.5. 接口回调注入

### 5.4. 依赖注入类型选择

### 5.5. 基础类型注入选择

### 5.6. 集合类型注入选择

### 5.7. 限定注入

### 5.8. 延迟依赖注入

### 5.9. 依赖处理过程

### 5.10. Autowired注入

### 5.11. Inject注入

### 5.12. Java通用注解注入原理

### 5.13. 自定义依赖注解注入原理


## 6. Bean作用域（Bean Scopes）



## 7. Bean生命周期（Bean Lifecycle）



## 8. 配置元信息（Configuration Metadata）



## 9. 资源管理（Resources）



## 10. Sping国际化



## 11. 校验（Validation）



## 12. 数据绑定（Data Binding）



## 13. 类型转换（Type Conversion）



## 14. 泛型处理（Generic Resolution）



## 15. Spring事件（Events）



## 16. Annotations



## 17. Environment抽象



## 18. 应用上下文生命周期（Container Lifecycle）



