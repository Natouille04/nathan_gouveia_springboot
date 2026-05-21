package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.ChildAlertResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.service.ChildAlertService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class ChildAlertController {
    private final ChildAlertService childAlertService;
    private static final Logger logger = LogManager.getLogger(ChildAlertController.class);

    public ChildAlertController(ChildAlertService ChildAlertService) {
        this.childAlertService = ChildAlertService;
    }

    @GetMapping("/childAlert")
    public List<ChildAlertResponseDTO> childAlert(@RequestParam(name = "address", defaultValue = "") String param) {
        logger.info("GET childAlert called with param : {}", param);
        return childAlertService.GetChildrenByAddress(param);
    }
}
