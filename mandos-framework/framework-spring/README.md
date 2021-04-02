
## 1. 总览

### 1.1. 编程模型

#### 1.1.1. 面向对象编程

基于契约接口实现

#### 1.1.2. 面向元编程

- 配置元信息
- 注解
- 属性配置

#### 1.1.3. 面向切面编程

- 动态代理

- 字节码提升

#### 1.1.4. 面向模块编程

#### 1.1.5. 面向函数编程

  Lambda

  Reactive：异步非阻塞

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

## 2. Spring Ioc

[Wiki](https://en.wikipedia.org/wiki/Inversion_of_control)

![20210401154939](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210401154939.png)

### 2.1. 实施技术

![20210401155227](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210401155227.png)

### 2.2. 輕量級容器鑒定標準

- 容器可以管理應用的代碼
- 容器可以快速啓動
- 容器本身對外界的依賴盡可能的小
- 容器輕量内存占用和最小API
- 容器可以管控

### 2.3. 好處

- 釋放單體應用
- 最大化代碼復用
- 更大程度的面嚮對象
- 更大程度的產品化
- 可測試性

### 2.4. Java Bean

### 2.5. 依賴查找VS依賴注入

![20210402093528](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210402093528.png)

#### 2.5.1. 構造器注入 Setter注入

## 5. Ioc依賴查找（Dependency Loopup）

![20210402105648](https://abram.oss-cn-shanghai.aliyuncs.com/blog/drunkard/20210402105648.png)

### 5.1. 通過名稱查找


### 5.2. 通過類型查找

#### 5.2.1. 單個Bean對象

#### 5.2.2. 集合Bean對象

#### 按照注解查找



## 6. Ioc依賴注入（Dependency Injection）



## 7. Ioc依賴来源（Dependency Sources）



## 8. Bean作用域（Bean Scopes）



## 9. Bean生命周期（Bean Lifecycle）



## 10. 配置元信息（Configuration Metadata）



## 11. 资源管理（Resources）



## 12. Sping国际化



## 13. 校验（Validation）



## 14. 数据绑定（Data Binding）



## 15. 类型转换（Type Conversion）



## 16. 泛型处理（Generic Resolution）



## 17. Spring事件（Events）



## 18. Annotations



## 19. Environment抽象



## 20. 应用上下文生命周期（Container Lifecycle）



