package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.CommunityEmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class CommunityEmailController {
    private CommunityEmailService communityEmailService;
    private static final Logger logger = LogManager.getLogger(CommunityEmailController.class);

    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    // Endpoint GET /communityEmail : Retourne une liste d'email des habitant de la ville spécifiée en paramètre

    @GetMapping("/communityEmail")
    public List<String> communityEmail(@RequestParam(name = "city", defaultValue = "") String param) {
        logger.info("GET communityEmail called with param : {}", param);
        return communityEmailService.GetEmailsByCity(param);
    }
}
