package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FirestationResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ChildAlertController {
    @GetMapping("/childAlert")
    public void firestation(@RequestParam(name = "address", defaultValue = "0") String param) {

    }
}
