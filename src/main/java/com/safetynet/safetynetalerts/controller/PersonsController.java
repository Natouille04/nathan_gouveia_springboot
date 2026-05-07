package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonsController {
    private final PersonsService personsService;

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    @PostMapping("/person")
    public ResponseEntity<Void> createPerson(@RequestBody Person newPerson) {
        if (personsService.save(newPerson)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody PersonUpdateDTO dto) {
        try {
            Person updated = personsService.update(firstName, lastName, dto);
            return ResponseEntity.ok(updated);
        }

        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(
            @PathVariable String firstName,
            @PathVariable String lastName
    ) {
        if(personsService.delete(firstName, lastName)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
