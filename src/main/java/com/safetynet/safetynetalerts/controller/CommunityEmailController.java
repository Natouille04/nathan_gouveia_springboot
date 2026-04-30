package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.CommunityEmailService;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommunityEmailController {
    private CommunityEmailService communityEmailService;

    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    @GetMapping("/communityEmail")
    public List<String> communityEmail(@RequestParam(name = "city", defaultValue = "") String param) {
        return communityEmailService.GetEmailsByCity(param);
    }
}
