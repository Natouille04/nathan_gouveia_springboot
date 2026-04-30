package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireResponseDTO;
import com.safetynet.safetynetalerts.service.FireService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireController {
    private final FireService fireService;

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @GetMapping("/fire")
    public FireResponseDTO fire(@RequestParam(name = "address", defaultValue = "") String param) {
        return fireService.PeopleByAddress(param);
    }
}
