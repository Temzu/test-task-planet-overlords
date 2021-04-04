package com.temzu.cosmos.repositories;

import com.temzu.cosmos.model.entities.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
