package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.PersonsController;
import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.PersonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonsControllerTestIT {
    private PersonsController personsController;
    private DataRepository dataRepository;

    @BeforeEach
    public void setUp() throws IOException {
        dataRepository = new DataRepository();
        PersonsService personsService = new PersonsService(dataRepository);
        personsController = new PersonsController(personsService);
    }

    @Test
    public void createPersonTest() {
        Person newPerson = new Person();
        newPerson.setFirstName("Bruce");
        newPerson.setLastName("Wayne");
        newPerson.setAddress("Wayne manor");
        newPerson.setCity("Gotham City");
        newPerson.setZip("00000");
        newPerson.setPhone("000-000-0000");
        newPerson.setEmail("BruceWayne@wayne.com");

        ResponseEntity<Void> response = personsController.createPerson(newPerson);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createPersonAlreadyExistsErrorTest() {
        Person existingPerson = new Person();
        existingPerson.setFirstName("John");
        existingPerson.setLastName("Boyd");
        existingPerson.setAddress("1509 Culver St");
        existingPerson.setCity("Culver");
        existingPerson.setZip("97451");
        existingPerson.setPhone("841-874-6512");
        existingPerson.setEmail("jaboyd@email.com");

        ResponseEntity<Void> response = personsController.createPerson(existingPerson);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void updatePersonTest() {
        PersonUpdateDTO newPerson = new PersonUpdateDTO();
        newPerson.setAddress("Wayne manor");
        newPerson.setCity("Gotham City");
        newPerson.setZip("00000");
        newPerson.setPhone("000-000-0000");
        newPerson.setEmail("BruceWayne@wayne.com");

        ResponseEntity<Person> response = personsController.updatePerson("John", "Boyd", newPerson);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals("Gotham City", response.getBody().getCity());
    }

    @Test
    public void updatePersonDoesNotExistTest() {
        PersonUpdateDTO newPerson = new PersonUpdateDTO();

        ResponseEntity<Person> response = personsController.updatePerson("Bruce", "Wayne", newPerson);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void deletePersonTest() {
        ResponseEntity<Void> response = personsController.deletePerson("John", "Boyd");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePersonDoesNotExistTest() {
        ResponseEntity<Void> response = personsController.deletePerson("Bruce", "Wayne");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
