package org.sherlock.mapstuct;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStuctCoverBasic {

    // 静态实例变量，用于提供通用的映射服务
    // 该映射器用于处理基本类型的覆盖映射操作
    MapStuctCoverBasic INSTANCE = Mappers.getMapper(MapStuctCoverBasic.class);

    MapStuctTestVO1 toVO1(MapStuctTest source);

    MapStuctTestVO2 toVO2(MapStuctTest source);

    List<MapStuctTestVO1> toCollectionVO1(List<MapStuctTest> sources);

    List<MapStuctTestVO2> toCollectionVO2(List<MapStuctTest> sources);

    // 字段类型不一致时
    @Mappings({
            @Mapping(target = "createTime", expression = "java(org.sherlock.mapstuct.MappingCoverUtils.localDateTimeCoverToString(source.getCreateTime()))"),
            @Mapping(target = "updateTime", expression = "java(org.sherlock.mapstuct.MappingCoverUtils.stringCoverToLocalDateTime(source.getUpdateTime()))"),
            @Mapping(target = "money", expression = "java(org.sherlock.mapstuct.MappingCoverUtils.DoubleCoverToInteger(source.getMoney()))")
    })
    MapStuctTestVO3 toVO3(MapStuctTest source);

    // 字段名称不一致时
    @Mappings({
            @Mapping(target = "userName", source = "name"),
            @Mapping(target = "userAge", source = "age")
    })
    MapStuctTestVO4 toVO4(MapStuctTest source);
}


