package com.temzu.cosmos.services;

import com.temzu.cosmos.exceptions.BadRequestException;
import com.temzu.cosmos.exceptions.ResourceNotFoundException;
import com.temzu.cosmos.model.dtos.OverlordFullDto;
import com.temzu.cosmos.model.dtos.OverlordSimpleDto;
import com.temzu.cosmos.model.entities.Overlord;
import com.temzu.cosmos.repositories.OverlordRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OverlordServiceImpl implements OverlordService {

    private final OverlordRepository overlordRepository;

    private final ModelMapper mapper;

    @Autowired
    public OverlordServiceImpl(OverlordRepository overlordRepository, ModelMapper mapper) {
        this.overlordRepository = overlordRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public OverlordSimpleDto saveOrUpdate(OverlordSimpleDto overlordDto) {
        if (overlordDto.getName().isBlank()) {
            throw new BadRequestException("The overlord cannot be without a name");
        }
        Overlord overlord = mapper.map(overlordDto, Overlord.class);
        return mapper.map(overlordRepository.save(overlord), OverlordSimpleDto.class);
    }

    @Override
    public Optional<OverlordSimpleDto> getOverlordById(Long id) {
        return overlordRepository.findById(id).map(overlord -> mapper.map(overlord, OverlordSimpleDto.class));
    }

    @Override
    @Transactional
    public Page<OverlordSimpleDto> getAllOverlords(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<OverlordSimpleDto> overlords = overlordRepository.findAll(pageable)
                .map(overlord -> mapper.map(overlord, OverlordSimpleDto.class));
        if (overlords.isEmpty()) {
            throw new ResourceNotFoundException("Overlords are absent");
        }
        checkPage(page, size, overlords);
        return overlords;
    }

    @Override
    @Transactional
    public Page<OverlordSimpleDto> getFreeOverlords(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<OverlordSimpleDto> freeOverlords = overlordRepository.findByPlanetsNull(pageable)
                .map(overlord -> mapper.map(overlord, OverlordSimpleDto.class));
        if (freeOverlords.isEmpty()) {
            throw new ResourceNotFoundException("All overlords have something to do");
        }
        checkPage(page, size, freeOverlords);
        return freeOverlords;
    }

    private void checkPage(int page, int size, Page<OverlordSimpleDto> freeOverlords) {
        int totalPages = freeOverlords.getTotalPages();
        if (totalPages < page) {
            throw new ResourceNotFoundException(
                    String.format("Page not found: page %d, size %d, total pages %d", page, size, totalPages));
        }
    }
}
