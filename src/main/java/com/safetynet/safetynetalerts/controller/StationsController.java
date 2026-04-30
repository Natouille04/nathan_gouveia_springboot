package com.safetynet.safetynetalerts.controller;

import java.util.List;

import com.safetynet.safetynetalerts.dto.AddressDTO;
import com.safetynet.safetynetalerts.service.StationsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Integer.parseInt;

@RestController
public class StationsController {
    private final StationsService stationsService;

    public StationsController(StationsService stationsService) {
        this.stationsService = stationsService;
    }

    @GetMapping("/flood/stations")
    public List<AddressDTO> stations(@RequestParam(name = "stations", defaultValue = "1") String param) {
        return stationsService.AddressByFirestation(Integer.parseInt(param));
    }
}