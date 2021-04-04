package com.temzu.cosmos.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanetDto {
    private Long id;
    private String name;
    private OverlordSimpleDto overlord;
}