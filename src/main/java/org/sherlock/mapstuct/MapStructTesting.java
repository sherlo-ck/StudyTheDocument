package org.sherlock.mapstuct;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class MapStructTesting {

    /**
     * 简单的类型转换
     */
    @Test
    public void simpleInstanceTest() {

        MapStuctTest mapStuctTest = MapStuctTest.builder().name("王伟").age(25).address("scd").build();

        MapStuctTestVO1 mapStuctTestVO1 = MapStuctCoverBasic.INSTANCE.toVO1(mapStuctTest);

        MapStuctTestVO2 mapStuctTestVO2 = MapStuctCoverBasic.INSTANCE.toVO2(mapStuctTest);

        log.info("mapStuctTestVO1:{}", mapStuctTestVO1);
        log.info("mapStuctTestVO2:{}", mapStuctTestVO2);
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
    }

    /**
     * 字段类型不一致时转化
     */
    @Test
    public void differentTypes() {

        MapStuctTest mapStuctTest = MapStuctTest.builder().name("王伟").age(25).address("scd").createTime(LocalDateTime.now()).updateTime("2024-09-18 23:32:11").money(11.1).build();

        MapStuctTestVO3 mapStuctTestVO3 = MapStuctCoverBasic.INSTANCE.toVO3(mapStuctTest);

        log.info("mapStuctTestVO3:{}", mapStuctTestVO3);
    }

}
