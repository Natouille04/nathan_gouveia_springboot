package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.PersonInfoLastNameResponseDTO;
import com.safetynet.safetynetalerts.service.PersonInfoLastNameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonInfoLastNameController {
    private final PersonInfoLastNameService personInfoLastNameService;

    public PersonInfoLastNameController(PersonInfoLastNameService personInfoLastNameService) {
        this.personInfoLastNameService = personInfoLastNameService;
    }

    @GetMapping("/personInfoLastName")
    public List<PersonInfoLastNameResponseDTO> personInfoLastName(@RequestParam(name = "lastName", defaultValue = "") String param) {
        return personInfoLastNameService.GetPersonByLastName(param);
    }
}
