package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.PhoneAlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneAlertController {
    private final PhoneAlertService phoneAlertService;

    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService = phoneAlertService;
    }

    @GetMapping("/phoneAlert")
    public List<String> phoneAlert(@RequestParam(name = "firestation", defaultValue = "0") int param) {
        return phoneAlertService.GetPhoneNumberByStation(param);
    }
}
