package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.PhoneAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneAlertController {
    private final PhoneAlertService phoneAlertService;
    private static final Logger logger = LogManager.getLogger(PhoneAlertController.class);

    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService = phoneAlertService;
    }

    @GetMapping("/phoneAlert")
    public List<String> phoneAlert(@RequestParam(name = "firestation", defaultValue = "0") int param) {
        logger.info("GET phoneAlert called with param : {}", param);
        return phoneAlertService.GetPhoneNumberByStation(param);
    }
}
