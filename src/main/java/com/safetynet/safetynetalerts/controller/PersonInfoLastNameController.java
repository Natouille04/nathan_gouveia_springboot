package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.PersonInfoLastNameResponseDTO;
import com.safetynet.safetynetalerts.service.PersonInfoLastNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonInfoLastNameController {
    private final PersonInfoLastNameService personInfoLastNameService;
    private static final Logger logger = LogManager.getLogger(PersonInfoLastNameController.class);

    public PersonInfoLastNameController(PersonInfoLastNameService personInfoLastNameService) {
        this.personInfoLastNameService = personInfoLastNameService;
    }

    // Endpoint GET /personInfoLastName : Retourne les informations d'une ou plusieurs personnes sélectionnées via le nom de famille donnée en paramètre

    @GetMapping("/personInfoLastName")
    public List<PersonInfoLastNameResponseDTO> personInfoLastName(@RequestParam(name = "lastName", defaultValue = "") String param) {
        logger.info("GET personInfoLastName called with param : {}", param);
        return personInfoLastNameService.GetPersonByLastName(param);
    }
}
