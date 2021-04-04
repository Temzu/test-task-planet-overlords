package com.temzu.cosmos.controllers;

import com.temzu.cosmos.model.dtos.OverlordSimpleDto;
import com.temzu.cosmos.services.OverlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/overlord")
public class OverlordController {

    private OverlordService overlordService;

    @Autowired
    public void setOverlordService(OverlordService overlordService) {
        this.overlordService = overlordService;
    }

    // если клиенту нужно топ 10 самых молодых клиетов, то он укажет "age" в параметре "sort_field"
    @GetMapping("/all")
    public Page<OverlordSimpleDto> getOverlords(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "sort_field", defaultValue = "id", required = false) String sortField,
            @RequestParam(name = "sort_dir", defaultValue = "ASC", required = false) String sortDir
    ) {
        if (page < 1) {
            page = 1;
        }
        Sort sort = sortDir.equals("DESC") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        return overlordService.getAllOverlords(page, 10, sort);
    }

    // показывает всех повелителей, которые не управляют планетами
    @GetMapping("/all/free")
    public Page<OverlordSimpleDto> getFreeOverlords(@RequestParam(defaultValue = "1") int page) {
        return overlordService.getFreeOverlords(page, 10);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OverlordSimpleDto addOverlord(@RequestBody OverlordSimpleDto overlord) {
        overlord.setId(null);
        return overlordService.saveOrUpdate(overlord);
    }
}
