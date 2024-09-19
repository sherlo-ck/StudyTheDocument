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
public class MapStuctTestVO4 {

    private String userName;

    private Integer userAge;

    private String address;

    private LocalDateTime createTime;

    private String updateTime;

    private Double money;
}
