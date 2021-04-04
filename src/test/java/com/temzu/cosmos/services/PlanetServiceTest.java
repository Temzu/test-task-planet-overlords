package com.temzu.cosmos.services;

import com.temzu.cosmos.exceptions.BadRequestException;
import com.temzu.cosmos.model.dtos.PlanetDto;
import com.temzu.cosmos.model.entities.Planet;
import com.temzu.cosmos.repositories.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanetServiceTest {

    @Autowired
    private PlanetService planetService;

    @MockBean
    private PlanetRepository planetRepository;

    @MockBean
    private ModelMapper mapper;

    @Test
    void saveOrUpdate_True_Test() {
        Planet planet = new Planet();
        PlanetDto planetDto = new PlanetDto();
        planetDto.setName("name");
        Mockito.doReturn(planet)
                .when(planetRepository)
                .save(planet);

        Mockito.doReturn(planetDto)
                .when(mapper)
                .map(planet, PlanetDto.class);

        Mockito.doReturn(planet)
                .when(mapper)
                .map(planetDto, Planet.class);

        assertNotNull(planetService.saveOrUpdate(planetDto));
    }

    @Test
    void saveOrUpdate_False_Test() {
        Planet planet = new Planet();
        PlanetDto planetDto = new PlanetDto();
        planetDto.setName("");
        Mockito.doReturn(planet)
                .when(planetRepository)
                .save(planet);

        Mockito.doReturn(planetDto)
                .when(mapper)
                .map(planet, PlanetDto.class);

        Mockito.doReturn(planet)
                .when(mapper)
                .map(planetDto, Planet.class);

        assertThrows(BadRequestException.class, () -> planetService.saveOrUpdate(planetDto));
    }

    @Test
    void deleteById_True_Test() {
        boolean isDeleted = planetService.deleteById(1L);
        assertTrue(isDeleted);
    }

    @Test
    void deleteById_False_Test() {
        Mockito.doThrow(new EmptyResultDataAccessException(1231))
                .when(planetRepository)
                .deleteById(1231L);

        boolean isDeleted = planetService.deleteById(1231L);
        assertFalse(isDeleted);
    }
}