package com.temzu.cosmos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temzu.cosmos.exceptions.BadRequestException;
import com.temzu.cosmos.exceptions.ResourceNotFoundException;
import com.temzu.cosmos.model.dtos.OverlordSimpleDto;
import com.temzu.cosmos.model.dtos.PlanetDto;
import com.temzu.cosmos.services.OverlordService;
import com.temzu.cosmos.services.PlanetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OverlordController.class)
class OverlordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OverlordService overlordService;

    @Test
    void getOverlords_True_Test() throws Exception {
        mockMvc.perform(get("/api/v1/overlord/all"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getOverlords_False_Test() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class)
                .when(overlordService)
                .getAllOverlords(1, 10, Sort.by("id"));

        mockMvc.perform(get("/api/v1/overlord/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFreeOverlords_True_Test() throws Exception {
        mockMvc.perform(get("/api/v1/overlord/all/free"))
                .andExpect(status().isOk());
    }

    @Test
    void getFreeOverlords_False_Test() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class)
                .when(overlordService)
                .getFreeOverlords(1, 10);

        mockMvc.perform(get("/api/v1/overlord/all/free"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addOverlord_True_Test() throws Exception {
        OverlordSimpleDto overlordSimpleDto = new OverlordSimpleDto();
        overlordSimpleDto.setName("name");

        Mockito.doReturn(overlordSimpleDto)
                .when(overlordService)
                .saveOrUpdate(overlordSimpleDto);

        mockMvc.perform(post("/api/v1/overlord")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(overlordSimpleDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void addOverlord_False_Test() throws Exception {
        OverlordSimpleDto overlordSimpleDto = new OverlordSimpleDto();

        Mockito.doThrow(BadRequestException.class)
                .when(overlordService)
                .saveOrUpdate(overlordSimpleDto);

        mockMvc.perform(post("/api/v1/overlord")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(overlordSimpleDto)))
                .andExpect(status().isBadRequest());
    }
}