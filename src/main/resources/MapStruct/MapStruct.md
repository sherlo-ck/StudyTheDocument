# 													MapStruct

## 1. 什么是MapStruct

专门用来生成对象与对象之间的映射关系的一个映射器，我们只需定义 mapper 接口，mapstruct 在编译的时候就会自动的帮我们实现这个映射接口，避免了麻烦复杂的映射实现。

## 2. 准备工作

### 2.1 引入Maven依赖

```xml
<properties>
    <org.mapstruct.version>1.6.0</org.mapstruct.version>
</properties>
<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
    <!-- IDEA可以在不添加mapstruct-processor依赖的情况下正常工作。但是，建议在项目中显式添加mapstruct-processor依赖，以确保在不同的IDE和构建工具之间保持一致性。这样可以避免在其他开发人员或CI环境中出现问题。-->
    <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
</dependencies>
```

引入依赖后如果在plugin中配置插件（如下）会导致项目中所有实体类中的**get/set方法失效**

```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>${org.mapstruct.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

解决办法：IDEA去插件市场中下载插件：**MapStruct support** 即可解决

2.2 下载插件 **MapStruct support**

![image-20240918003301781.png](image-20240918003301781.png)

### 2.3 准备简单例子

```java
// 测试类
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapStuctTest {

    private String name;

    private Integer age;

    private String address;
}

// 与测试类相同
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapStuctTestVO1 {

    private String name;

    private Integer age;

    private String address;
}

// 比测试类少一个字段address
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapStuctTestVO2 {

    private String name;

    private Integer age;
}

// 转换接口
@Mapper(componentModel = "spring")
public interface MapStuctCoverBasic {

    // 静态实例变量，用于提供通用的映射服务
    // 该映射器用于处理基本类型的覆盖映射操作
    MapStuctCoverBasic INSTANCE = Mappers.getMapper(MapStuctCoverBasic.class);

    MapStuctTestVO1 toVO1(MapStuctTest test);

    MapStuctTestVO2 toVO2(MapStuctTest test);
}


// 具体测试案例
@Slf4j
public class MapStructTesting {
    @Test
    public void simpleInstanceTest() {

        MapStuctTest mapStuctTest = MapStuctTest.builder().name("王伟").age(25).address("scd").build();

        MapStuctTestVO1 mapStuctTestVO1 = MapStuctCoverBasic.INSTANCE.toVO1(mapStuctTest);

        MapStuctTestVO2 mapStuctTestVO2 = MapStuctCoverBasic.INSTANCE.toVO2(mapStuctTest);

        log.info("mapStuctTestVO1:{}", mapStuctTestVO1);
        log.info("mapStuctTestVO2:{}", mapStuctTestVO2);
        // 输出结果
        // mapStuctTestVO1:MapStuctTestVO1(name=王伟, age=25, address=scd)
        // mapStuctTestVO2:MapStuctTestVO2(name=王伟, age=25)
    }   
}



```

