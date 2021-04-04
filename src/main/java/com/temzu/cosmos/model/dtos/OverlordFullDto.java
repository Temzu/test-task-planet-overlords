package com.temzu.cosmos.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OverlordFullDto {
    private Long id;
    private String name;
    private Long age;
    private List<PlanetDto> planets;
}
