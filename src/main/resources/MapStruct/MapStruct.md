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

### 2.2 下载插件 **MapStruct support**

![image-20240918003301781.png](image-20240918003301781.png)

## 3 测试

### 3.1 准备简单的例子

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

### 3.2 集合类型转换

```java
@Mapper(componentModel = "spring")
public interface MapStuctCoverBasic {

    // 静态实例变量，用于提供通用的映射服务
    // 该映射器用于处理基本类型的覆盖映射操作
    MapStuctCoverBasic INSTANCE = Mappers.getMapper(MapStuctCoverBasic.class);
    
    List<MapStuctTestVO1> toCollectionVO1(List<MapStuctTest> sources);

    List<MapStuctTestVO2> toCollectionVO2(List<MapStuctTest> sources);
}

    /**
     * 集合类型转换
     */
    @Test
    public void simpleInstanceCollectionTest() {
        MapStuctTest mapStuctTest1 = MapStuctTest.builder().name("王伟1").age(251).address("scd1").build();
        MapStuctTest mapStuctTest2 = MapStuctTest.builder().name("王伟2").age(252).address("scd2").build();
        MapStuctTest mapStuctTest3 = MapStuctTest.builder().name("王伟3").age(253).address("scd3").build();
        List<MapStuctTest> mapStuctTests = Lists.newArrayList(mapStuctTest1, mapStuctTest2, mapStuctTest3);

        List<MapStuctTestVO1> mapStuctTestVO1s = MapStuctCoverBasic.INSTANCE.toCollectionVO1(mapStuctTests);
        List<MapStuctTestVO2> mapStuctTestVO2s = MapStuctCoverBasic.INSTANCE.toCollectionVO2(mapStuctTests);
        
        log.info("mapStuctTestVO1:{}", JSON.toJSONString(mapStuctTestVO1s));
        log.info("mapStuctTestVO2:{}", JSON.toJSONString(mapStuctTestVO2s));
        // 输出结果
        // mapStuctTestVO1:[{"address":"scd1","age":251,"name":"王伟1"},{"address":"scd2","age":252,"name":"王伟2"},{"address":"scd3","age":253,"name":"王伟3"}]
        // mapStuctTestVO2:[{"age":251,"name":"王伟1"},{"age":252,"name":"王伟2"},{"age":253,"name":"王伟3"}]
    }
```

### 3.3 字段类型不一致转换

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapStuctTestVO3 {

    private String name;

    private Integer age;

    private String createTime;

    private LocalDateTime updateTime;

    private Integer money;
}

@Mapper(componentModel = "spring")
public interface MapStuctCoverBasic {

    // 静态实例变量，用于提供通用的映射服务
    // 该映射器用于处理基本类型的覆盖映射操作
    MapStuctCoverBasic INSTANCE = Mappers.getMapper(MapStuctCoverBasic.class);

    // 字段类型不一致时
    @Mappings({
            @Mapping(target = "createTime", expression = "java(org.sherlock.tool_test.mapstuct.MappingCoverUtils.localDateTimeCoverToString(source.getCreateTime()))"),
            @Mapping(target = "updateTime", expression = "java(org.sherlock.tool_test.mapstuct.MappingCoverUtils.stringCoverToLocalDateTime(source.getUpdateTime()))"),
            @Mapping(target = "money", expression = "java(org.sherlock.tool_test.mapstuct.MappingCoverUtils.DoubleCoverToInteger(source.getMoney()))")
    })
    MapStuctTestVO3 toVO3(MapStuctTest source);
}


/**
 * 引用这个类下类型转换方法
 * 1、基本类型及其他们对应的包装类型, 会自动进行拆装箱, 不需要人为的处理
 * 2、基本类型的包装类型和string类型之间, 不需要人为的处理
 */
@Slf4j
public class MappingCoverUtils {

    public static String localDateTimeCoverToString(LocalDateTime source) {
        assert source != null : "localDateTime is null";
        return source.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString();
    }

    public static LocalDateTime stringCoverToLocalDateTime(String source) {
        assert StringUtils.isNotEmpty(source) : "localDateTime is null";
        return DateUtil.parseLocalDateTime(source);
    }

    public static Integer DoubleCoverToInteger(Double source) {
        assert source != null : "source is null";
        return source.intValue();
    }
}

    /**
     * 字段类型不一致时转化
     */
    @Test
    public void differentTypes() {

        MapStuctTest mapStuctTest = MapStuctTest.builder().name("王伟").age(25).address("scd").createTime(LocalDateTime.now()).updateTime("2024-09-18 23:32:11").money(11.1).build();

        MapStuctTestVO3 mapStuctTestVO3 = MapStuctCoverBasic.INSTANCE.toVO3(mapStuctTest);

        log.info("mapStuctTestVO3:{}", mapStuctTestVO3);
        // 输出结果
        // mapStuctTestVO3:MapStuctTestVO3(name=王伟, age=25, createTime=20240918, updateTime=2024-09-18T23:32:11, money=11)
    }
```

3.4 字段不一致转换

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapStuctTestVO4 {

    private String userName;

    private Integer userAge;

    private String address;

    private LocalDateTime createTime;

    private String updateTime;

    private Double money;
}

@Mapper(componentModel = "spring")
public interface MapStuctCoverBasic {
    // 字段名称不一致时
    @Mappings({
            @Mapping(target = "userName", source = "name"),
            @Mapping(target = "userAge", source = "age")
    })
    MapStuctTestVO4 toVO4(MapStuctTest source);
}

    /**
     * 字段不一致时转化
     */
    @Test
    public void fieldDifferent() {

        MapStuctTest mapStuctTest = MapStuctTest.builder().name("王伟").age(25).address("scd").createTime(LocalDateTime.now()).updateTime("2024-09-18 23:32:11").money(11.1).build();

        MapStuctTestVO4 mapStuctTestVO4 = MapStuctCoverBasic.INSTANCE.toVO4(mapStuctTest);

        log.info("mapStuctTestVO4:{}", mapStuctTestVO4);
    }
```

