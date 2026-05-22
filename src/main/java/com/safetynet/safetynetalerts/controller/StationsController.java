package com.safetynet.safetynetalerts.controller;

import java.util.List;

import com.safetynet.safetynetalerts.dto.AddressDTO;
import com.safetynet.safetynetalerts.service.StationsService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Integer.parseInt;

@RestController
public class StationsController {
    private final StationsService stationsService;
    private static final Logger logger = LogManager.getLogger();

    public StationsController(StationsService stationsService) {
        this.stationsService = stationsService;
    }

    // Endpoint GET /firestation : Retourne une liste d'adresse sélectionnée via le numéro de station donnée en paramètre

    @GetMapping("/flood/stations")
    public List<AddressDTO> stations(@RequestParam(name = "stations", defaultValue = "") int param) {
        logger.info("GET flood/stations called with param : {}", param);
        return stationsService.AddressByFirestation(param);
    }
}