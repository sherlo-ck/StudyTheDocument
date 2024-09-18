package org.sherlock.mapstuct;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapStuctTestVO1 {

    private String name;

    private Integer age;

    private String address;
}
