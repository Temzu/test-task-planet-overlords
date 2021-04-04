package com.temzu.cosmos.services;

import com.temzu.cosmos.exceptions.BadRequestException;
import com.temzu.cosmos.exceptions.ResourceNotFoundException;
import com.temzu.cosmos.model.dtos.OverlordSimpleDto;
import com.temzu.cosmos.model.dtos.PlanetDto;
import com.temzu.cosmos.model.entities.Overlord;
import com.temzu.cosmos.model.entities.Planet;
import com.temzu.cosmos.repositories.OverlordRepository;
import com.temzu.cosmos.repositories.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OverlordServiceTest {

    @Autowired
    private OverlordService overlordService;

    @MockBean
    private OverlordRepository overlordRepository;

    @MockBean
    private ModelMapper mapper;

    @Test
    void saveOrUpdate_True_Test() {
        Overlord overlord = new Overlord();
        OverlordSimpleDto overlordSimpleDto = new OverlordSimpleDto();
        overlordSimpleDto.setName("name");
        Mockito.doReturn(overlord)
                .when(overlordRepository)
                .save(overlord);

        Mockito.doReturn(overlordSimpleDto)
                .when(mapper)
                .map(overlord, OverlordSimpleDto.class);

        Mockito.doReturn(overlord)
                .when(mapper)
                .map(overlordSimpleDto, Overlord.class);

        assertNotNull(overlordService.saveOrUpdate(overlordSimpleDto));
    }

    @Test
    void saveOrUpdate_False_Test() {
        Overlord overlord = new Overlord();
        OverlordSimpleDto overlordSimpleDto = new OverlordSimpleDto();
        overlordSimpleDto.setName("");
        Mockito.doReturn(overlord)
                .when(overlordRepository)
                .save(overlord);

        Mockito.doReturn(overlordSimpleDto)
                .when(mapper)
                .map(overlord, OverlordSimpleDto.class);

        Mockito.doReturn(overlord)
                .when(mapper)
                .map(overlordSimpleDto, Overlord.class);

        assertThrows(BadRequestException.class, () -> overlordService.saveOrUpdate(overlordSimpleDto));
    }

    @Test
    void getOverlordById() {
        Overlord overlord = new Overlord();
        overlord.setId(1L);
        overlord.setName("Overlord");
        overlord.setAge(12123213L);
        Mockito.doReturn(Optional.of(overlord))
                .when(overlordRepository)
                .findById(1L);

        Mockito.doReturn(new OverlordSimpleDto())
                .when(mapper)
                .map(overlord, OverlordSimpleDto.class);

        assertTrue(overlordService.getOverlordById(1L).isPresent());
    }

    @Test
    void getAllOverlords_True_Test() {
        Overlord overlord = new Overlord();
        overlord.setId(1L);
        overlord.setName("Overlord");
        overlord.setAge(12123213L);

        Page<Overlord> overlords = new PageImpl<>(Collections.singletonList(overlord));

        Mockito.doReturn(overlords)
                .when(overlordRepository)
                .findAll(PageRequest.of(0, 10, Sort.by("id")));

        Mockito.doReturn(new OverlordSimpleDto())
                .when(mapper)
                .map(overlord, OverlordSimpleDto.class);

        assertFalse(overlordService.getAllOverlords(1, 10, Sort.by("id")).isEmpty());
    }

    @Test
    void getAllOverlords_False_Test() {
        Overlord overlord = new Overlord();
        Page<Overlord> overlords = new PageImpl<>(Collections.emptyList());

        Mockito.doReturn(overlords)
                .when(overlordRepository)
                .findAll(PageRequest.of(0, 10, Sort.by("id")));

        Mockito.doReturn(new OverlordSimpleDto())
                .when(mapper)
                .map(overlord, OverlordSimpleDto.class);

        assertThrows(ResourceNotFoundException.class, () -> overlordService.getAllOverlords(1, 10, Sort.by("id")));
    }

    @Test
    void getAllOverlords_False_Test_Total_Page() {
        Overlord overlord = new Overlord();
        overlord.setId(1L);
        overlord.setName("Overlord");
        overlord.setAge(12123213L);

        Page<Overlord> overlords = new PageImpl<>(Collections.singletonList(overlord));

        Mockito.doReturn(overlords)
                .when(overlordRepository)
                .findAll(PageRequest.of(5, 10, Sort.by("id")));

        Mockito.doReturn(new OverlordSimpleDto())
                .when(mapper)
                .map(overlord, OverlordSimpleDto.class);

        assertThrows(ResourceNotFoundException.class, () -> overlordService.getAllOverlords(6, 10, Sort.by("id")));
    }

    @Test
    void getFreeOverlords_True_Test() {
        Overlord overlord = new Overlord();
        overlord.setId(1L);
        overlord.setName("Overlord");
        overlord.setAge(12123213L);

        Page<Overlord> overlords = new PageImpl<>(Collections.singletonList(overlord));

        Mockito.doReturn(overlords)
                .when(overlordRepository)
                .findByPlanetsNull(PageRequest.of(0, 10));

        Mockito.doReturn(new OverlordSimpleDto())
                .when(mapper)
                .map(overlord, OverlordSimpleDto.class);

        assertFalse(overlordService.getFreeOverlords(1, 10).isEmpty());
    }

    @Test
    void getFreeOverlords_False_Test() {
        Overlord overlord = new Overlord();
        Page<Overlord> overlords = new PageImpl<>(Collections.emptyList());

        Mockito.doReturn(overlords)
                .when(overlordRepository)
                .findByPlanetsNull(PageRequest.of(0, 10));

        Mockito.doReturn(new OverlordSimpleDto())
                .when(mapper)
                .map(overlord, OverlordSimpleDto.class);

        assertThrows(ResourceNotFoundException.class, () -> overlordService.getFreeOverlords(1, 10));
    }
}