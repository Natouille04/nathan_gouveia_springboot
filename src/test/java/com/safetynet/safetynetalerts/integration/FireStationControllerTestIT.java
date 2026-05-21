package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.FireStationsController;
import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.FireStationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FireStationControllerTestIT {
    private FireStationsController fireStationsController;

    @BeforeEach
    public void setUp() throws IOException {
        DataRepository dataRepository = new DataRepository();
        FireStationsService fireStationsService = new FireStationsService(dataRepository);
        this.fireStationsController = new FireStationsController(fireStationsService);
    }

    @Test
    public void createFireStationsTest() {
        Firestation newFireStation = new Firestation();
        newFireStation.setStation("1");
        newFireStation.setAddress("Wayne Manor");

        ResponseEntity<Void> response = fireStationsController.addFireStation(newFireStation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createFireStationsAlreadyExistsErrorTest() {
        Firestation existingFireStations = new Firestation();
        existingFireStations.setStation("3");
        existingFireStations.setAddress("1509 Culver St");

        ResponseEntity<Void> response = fireStationsController.addFireStation(existingFireStations);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void updateFireStationsTest() {
        FireStationUpdateDTO newFireStation = new FireStationUpdateDTO();
        newFireStation.setStation("1000");
        newFireStation.setAddress("Wayne Manor");

        ResponseEntity<Firestation> response = fireStationsController.updateFireStation("1509 Culver St", newFireStation);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Wayne Manor", response.getBody().getAddress());
    }

    @Test
    public void updateFireStationsDoesNotExistTest() {
        FireStationUpdateDTO newFireStation = new FireStationUpdateDTO();

        ResponseEntity<Firestation> response = fireStationsController.updateFireStation("Wayne Manor", newFireStation);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void deletePersonTest() {
        ResponseEntity<Void> response = fireStationsController.deleteFireStation("1509 Culver St");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePersonDoesNotExistTest() {
        ResponseEntity<Void> response = fireStationsController.deleteFireStation("Wayne Manor");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
