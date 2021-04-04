package com.temzu.cosmos.services;

import com.temzu.cosmos.model.dtos.OverlordSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface OverlordService {
    OverlordSimpleDto saveOrUpdate(OverlordSimpleDto overlordDto);

    Optional<OverlordSimpleDto> getOverlordById(Long id);

    Page<OverlordSimpleDto> getAllOverlords(int page, int size, Sort sort );

    Page<OverlordSimpleDto> getFreeOverlords(int page, int size);
}
