package com.safetynet.safetynetalerts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dto.FirestationResponseDTO;
import com.safetynet.safetynetalerts.service.StationNumberService;

import java.util.Set;

@RestController
public class StationNumberController {

    private final StationNumberService stationNumberService;

    public StationNumberController(StationNumberService stationNumberService) {
        this.stationNumberService = stationNumberService;
    }

    @GetMapping("/firestation")
    public FirestationResponseDTO firestation(@RequestParam(name = "stationNumber", defaultValue = "0") int param) {
        return stationNumberService.PersonByFireStation(param);
    }
}