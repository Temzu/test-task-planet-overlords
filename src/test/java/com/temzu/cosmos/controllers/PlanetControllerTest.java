package com.temzu.cosmos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temzu.cosmos.exceptions.BadRequestException;
import com.temzu.cosmos.exceptions.ResourceNotFoundException;
import com.temzu.cosmos.model.dtos.PlanetDto;
import com.temzu.cosmos.services.OverlordService;
import com.temzu.cosmos.services.PlanetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlanetController.class)
class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetService planetService;

    @MockBean
    private OverlordService overlordService;

    @Test
    void addPlanet_True_Test() throws Exception {
        PlanetDto planetDto = new PlanetDto();
        planetDto.setName("name");

        Mockito.doReturn(planetDto)
                .when(planetService)
                .saveOrUpdate(planetDto);

        mockMvc.perform(post("/api/v1/planet")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(planetDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void addPlanet_False_Test() throws Exception {
        PlanetDto planetDto = new PlanetDto();

        Mockito.doThrow(BadRequestException.class)
                .when(planetService)
                .saveOrUpdate(planetDto);

        mockMvc.perform(post("/api/v1/planet")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(planetDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePlanet_True_Test() throws Exception {
        PlanetDto planetDto = new PlanetDto();
        planetDto.setId(1L);

        mockMvc.perform(put("/api/v1/planet")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(planetDto))
                .param("idOverlord", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePlanet_False_Test() throws Exception {
        PlanetDto planetDto = new PlanetDto();

        Mockito.doThrow(ResourceNotFoundException.class)
                .when(overlordService)
                .getOverlordById(5L);

        mockMvc.perform(put("/api/v1/planet")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(planetDto)))
                .andExpect(status().isBadRequest());

        planetDto.setId(1L);

        mockMvc.perform(put("/api/v1/planet")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(planetDto))
                .param("overlord_id", "5"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePlanet_True_Test() throws Exception {
        Mockito.doReturn(true)
                .when(planetService)
                .deleteById(1L);

        mockMvc.perform(delete("/api/v1/planet/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void deletePlanet_False_Test() throws Exception {
        Mockito.doReturn(false)
                .when(planetService)
                .deleteById(1L);

        mockMvc.perform(delete("/api/v1/planet/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}