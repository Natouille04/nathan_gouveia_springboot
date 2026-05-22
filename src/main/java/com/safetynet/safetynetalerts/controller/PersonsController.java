package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonsController {
    private final PersonsService personsService;
    private static final Logger logger = LogManager.getLogger(PersonsController.class);

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    // Endpoint POST /person : Ajoute une personne selon les informations données dans le corps de la requète

    @PostMapping("/person")
    public ResponseEntity<Void> createPerson(@RequestBody Person newPerson) {
        logger.info("POST person called with Request body");

        if (personsService.save(newPerson)) {
            return ResponseEntity.ok().build();
        }

        logger.error("POST ERROR person : Conflict with already existing data");
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // Endpoint PATCH /person : Modifie une personne selon les informations données dans le corps de la requète

    @PatchMapping("/person")
    public ResponseEntity<Person> updatePerson(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestBody PersonUpdateDTO dto
    ) {
        try {
            logger.info("PATCH person called with param : {}, {}, and Request body", firstName, lastName);
            Person updated = personsService.update(firstName, lastName, dto);
            return ResponseEntity.ok(updated);
        }

        catch (RuntimeException e) {
            logger.error("PATCH ERROR person : firstName '{}' and/or lastName '{}' not found", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint DELETE /person : Supprime une personne selon les informations données dans le paramètre de la requète

    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        try {
            logger.info("DELETE person called with param : {}, {}, and Request body", firstName, lastName);

            personsService.delete(firstName, lastName);
            return ResponseEntity.ok().build();
        }

        catch (RuntimeException e) {
            logger.error("DELETE ERROR person : firstName '{}' and/or lastName '{}' not found", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }
}
