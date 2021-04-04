package com.temzu.cosmos.services;

import com.temzu.cosmos.model.dtos.PlanetDto;

public interface PlanetService {
    PlanetDto saveOrUpdate(PlanetDto planetDto);

    boolean deleteById(Long id);
}
