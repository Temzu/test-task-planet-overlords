package com.temzu.cosmos.services;

import com.temzu.cosmos.exceptions.BadRequestException;
import com.temzu.cosmos.model.dtos.PlanetDto;
import com.temzu.cosmos.model.entities.Planet;
import com.temzu.cosmos.repositories.PlanetRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;

    private final ModelMapper mapper;

    @Autowired
    public PlanetServiceImpl(PlanetRepository planetRepository, ModelMapper mapper, OverlordService overlordService) {
        this.planetRepository = planetRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PlanetDto saveOrUpdate(PlanetDto planetDto) {
        if (planetDto.getName().isBlank()) {
            throw new BadRequestException("The planet cannot be without a name");
        }
        Planet planet = mapper.map(planetDto, Planet.class);
        return mapper.map(planetRepository.save(planet), PlanetDto.class);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            planetRepository.deleteById(id);
            log.info("planet with id: {} deleted", id);
            return true;
        } catch (Exception e) {
            log.info("planet with id: {} not found", id);
            return false;
        }
    }
}
