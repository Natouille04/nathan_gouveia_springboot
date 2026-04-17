package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.ChildAlertResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.service.ChildAlertService;

import java.util.List;

@RestController
public class ChildAlertController {
    private final ChildAlertService childAlertService;

    public ChildAlertController(ChildAlertService ChildAlertService) {
        this.childAlertService = ChildAlertService;
    }

    @GetMapping("/childAlert")
    public List<ChildAlertResponseDTO> childAlert(@RequestParam(name = "address", defaultValue = "") String param) {
        return childAlertService.GetChildrenByAddress(param);
    }
}
