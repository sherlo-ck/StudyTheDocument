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
public class MapStuctTestVO3 {

    private String name;

    private Integer age;

    private String createTime;

    private LocalDateTime updateTime;

    private Integer money;
}
