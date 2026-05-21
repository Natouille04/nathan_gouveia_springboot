package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.FireStationsController;
import com.safetynet.safetynetalerts.controller.MedicalRecordController;
import com.safetynet.safetynetalerts.controller.PersonsController;
import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.FireStationsService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicalRecordControllerTestIT {
    private MedicalRecordController medicalRecordController;
    private DataRepository dataRepository;

    @BeforeEach
    public void setUp() throws IOException {
        dataRepository = new DataRepository();
        MedicalRecordService medicalRecordService = new MedicalRecordService(dataRepository);
        medicalRecordController = new MedicalRecordController(medicalRecordService);
    }

    @Test
    public void createPersonTest() {
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setFirstName("Bruce");
        newMedicalRecord.setLastName("Wayne");
        newMedicalRecord.setBirthdate("30/03/1939");
        newMedicalRecord.setMedications(new ArrayList<>());
        newMedicalRecord.setAllergies(new ArrayList<>());

        ResponseEntity<Void> response = medicalRecordController.addMedicalRecord(newMedicalRecord);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createPersonAlreadyExistsErrorTest() {
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setFirstName("John");
        newMedicalRecord.setLastName("Boyd");
        newMedicalRecord.setBirthdate("03/06/1984");
        newMedicalRecord.setMedications(new ArrayList<>());
        newMedicalRecord.setAllergies(new ArrayList<>());

        ResponseEntity<Void> response = medicalRecordController.addMedicalRecord(newMedicalRecord);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void updatePersonTest() {
        MedicalUpdateDTO newMedicalRecord = new MedicalUpdateDTO();
        newMedicalRecord.setBirthdate("03/06/1984");
        newMedicalRecord.setMedications(new ArrayList<>());
        newMedicalRecord.setAllergies(new ArrayList<>());

        ResponseEntity<MedicalRecord> response = medicalRecordController.updateMedicalRecord("John", "Boyd", newMedicalRecord);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals("03/06/1984", response.getBody().getBirthdate());
    }

    @Test
    public void updatePersonDoesNotExistTest() {
        MedicalUpdateDTO newMedicalRecord = new MedicalUpdateDTO();

        ResponseEntity<MedicalRecord> response = medicalRecordController.updateMedicalRecord("Bruce", "Wayne", newMedicalRecord);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void deletePersonTest() {
        ResponseEntity<Void> response = medicalRecordController.deleteMedicalRecord("John", "Boyd");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePersonDoesNotExistTest() {
        ResponseEntity<Void> response = medicalRecordController.deleteMedicalRecord("Bruce", "Wayne");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
