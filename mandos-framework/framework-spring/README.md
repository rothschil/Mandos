<!-- TOC -->

- [1. 总览](#1-总览)
    - [1.1. 编程模型](#11-编程模型)
    - [1.2. 環境準備](#12-環境準備)
    - [1.3. 特性總覽](#13-特性總覽)
    - [1.4. 版本特性](#14-版本特性)
    - [1.5. 模塊化設計](#15-模塊化設計)
- [2. Spring Ioc基本概述](#2-spring-ioc基本概述)
    - [2.1. 概要说明](#21-概要说明)
        - [2.1.1. 实施技术](#211-实施技术)
        - [2.1.2. 輕量級容器鑒定標準](#212-輕量級容器鑒定標準)
        - [2.1.3. 好處](#213-好處)
    - [2.2. 依賴注入和依賴查找比较](#22-依賴注入和依賴查找比较)
        - [2.2.1. Ioc依賴查找（Dependency Loopup）](#221-ioc依賴查找dependency-loopup)
            - [2.2.1.1. 通過名稱查找](#2211-通過名稱查找)
            - [2.2.1.2. 通過類型查找](#2212-通過類型查找)
                - [2.2.1.2.1. 單個Bean對象](#22121-單個bean對象)
                - [2.2.1.2.2. 集合Bean對象](#22122-集合bean對象)
                - [2.2.1.2.3. 按照注解查找](#22123-按照注解查找)
        - [2.2.2. Ioc依賴注入（Dependency Injection）](#222-ioc依賴注入dependency-injection)
            - [2.2.2.1. 手動方式](#2221-手動方式)
            - [2.2.2.2. autowire方式](#2222-autowire方式)
        - [2.2.3. Ioc依賴来源（Dependency Sources）](#223-ioc依賴来源dependency-sources)
        - [2.2.4. 配置元信息](#224-配置元信息)
        - [2.2.5. IOC容器](#225-ioc容器)
        - [2.2.6. 容器上下文](#226-容器上下文)
        - [2.2.7. 使用Spring IOC容器](#227-使用spring-ioc容器)
        - [2.2.8. Spring IOC容器生命周期](#228-spring-ioc容器生命周期)
- [3. Bean基础](#3-bean基础)
    - [3.1. 定义Bean](#31-定义bean)
    - [3.2. BeanDefinition元信息](#32-beandefinition元信息)
        - [3.2.1. BeanDefinitionBuilder](#321-beandefinitionbuilder)
        - [3.2.2. AbstractBeanDefinition](#322-abstractbeandefinition)
    - [3.3. 命名Spring Bean](#33-命名spring-bean)
    - [3.4. Spring Bean 别名](#34-spring-bean-别名)
    - [3.5. 注册Spring Bean](#35-注册spring-bean)
    - [3.6. 实例化Spring Bean](#36-实例化spring-bean)
        - [3.6.1. 常规方式](#361-常规方式)
        - [3.6.2. 特殊方式](#362-特殊方式)
    - [3.7. 初始化Spring Bean](#37-初始化spring-bean)
    - [3.8. 延迟初始化Spring Bean](#38-延迟初始化spring-bean)
    - [3.9. 销毁Spring Bean](#39-销毁spring-bean)
    - [3.10. 垃圾回收Spring Bean](#310-垃圾回收spring-bean)
- [4. Bean作用域（Bean Scopes）](#4-bean作用域bean-scopes)
- [5. Bean生命周期（Bean Lifecycle）](#5-bean生命周期bean-lifecycle)
- [6. 配置元信息（Configuration Metadata）](#6-配置元信息configuration-metadata)
- [7. 资源管理（Resources）](#7-资源管理resources)
- [8. Sping国际化](#8-sping国际化)
- [9. 校验（Validation）](#9-校验validation)
- [10. 数据绑定（Data Binding）](#10-数据绑定data-binding)
- [11. 类型转换（Type Conversion）](#11-类型转换type-conversion)
- [12. 泛型处理（Generic Resolution）](#12-泛型处理generic-resolution)
- [13. Spring事件（Events）](#13-spring事件events)
- [14. Annotations](#14-annotations)
- [15. Environment抽象](#15-environment抽象)
- [16. 应用上下文生命周期（Container Lifecycle）](#16-应用上下文生命周期container-lifecycle)

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



## 4. Bean作用域（Bean Scopes）



## 5. Bean生命周期（Bean Lifecycle）



## 6. 配置元信息（Configuration Metadata）



## 7. 资源管理（Resources）



## 8. Sping国际化



## 9. 校验（Validation）



## 10. 数据绑定（Data Binding）



## 11. 类型转换（Type Conversion）



## 12. 泛型处理（Generic Resolution）



## 13. Spring事件（Events）



## 14. Annotations



## 15. Environment抽象



## 16. 应用上下文生命周期（Container Lifecycle）



