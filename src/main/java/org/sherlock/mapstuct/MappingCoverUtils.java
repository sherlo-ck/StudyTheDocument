package org.sherlock.mapstuct;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
