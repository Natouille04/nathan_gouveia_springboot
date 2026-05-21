package com.safetynet.safetynetalerts.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dto.FirestationResponseDTO;
import com.safetynet.safetynetalerts.service.StationNumberService;

@RestController
public class StationNumberController {
    private final StationNumberService stationNumberService;
    private static final Logger logger = LogManager.getLogger();

    public StationNumberController(StationNumberService stationNumberService) {
        this.stationNumberService = stationNumberService;
    }

    @GetMapping("/firestation")
    public FirestationResponseDTO firestation(@RequestParam(name = "stationNumber", defaultValue = "0") int param) {
        logger.info("GET firestation called with param : {}", param);
        return stationNumberService.PersonByFireStation(param);
    }
}