package com.temzu.cosmos.controllers;

import com.temzu.cosmos.exceptions.BadRequestException;
import com.temzu.cosmos.exceptions.ResourceNotFoundException;
import com.temzu.cosmos.model.dtos.PlanetDto;
import com.temzu.cosmos.services.OverlordService;
import com.temzu.cosmos.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/planet")
public class PlanetController {

    private PlanetService planetService;

    private OverlordService overlordService;

    @Autowired
    public void setOverlordService(OverlordService overlordService) {
        this.overlordService = overlordService;
    }

    @Autowired
    public void setPlanetService(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanetDto addPlanet(@RequestBody PlanetDto planetDto) {
        planetDto.setId(null);
        return planetService.saveOrUpdate(planetDto);
    }

    // Если клиент хочет назначить повелителя у планеты то в параметре "overlord_id" указывает id нужного повелителя
    @PutMapping
    public PlanetDto updatePlanet(
            @RequestBody PlanetDto planetDto,
            @RequestParam(name = "overlord_id", required = false) Long idOverlord
    ) {
        if (planetDto.getId() == null) {
            throw new BadRequestException("Id of planet is null");
        }
        if (idOverlord != null && idOverlord >= 1) {
            planetDto.setOverlord(overlordService.getOverlordById(idOverlord).orElseThrow(() ->
                    new ResourceNotFoundException("Overlord with id: " + idOverlord + " not found")));
        }
        return planetService.saveOrUpdate(planetDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlanet(@PathVariable Long id) {
        if (planetService.deleteById(id)) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Planet with id: " + id + " not found");
        }
    }

}
