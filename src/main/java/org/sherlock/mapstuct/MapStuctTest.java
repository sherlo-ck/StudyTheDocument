package org.sherlock.mapstuct;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapStuctTest {

    private String name;

    private Integer age;

    private String address;

    private LocalDateTime createTime;

    private String updateTime;

    private Double money;
}
