package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireResponseDTO;
import com.safetynet.safetynetalerts.service.FireService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireController {
    private final FireService fireService;
    private static final Logger logger = LogManager.getLogger(FireController.class);

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @GetMapping("/fire")
    public FireResponseDTO fire(@RequestParam(name = "address", defaultValue = "") String param) {
        logger.info("GET fire called with param : {}", param);
        return fireService.PeopleByAddress(param);
    }
}
