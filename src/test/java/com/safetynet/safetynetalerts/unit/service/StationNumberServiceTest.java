package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.dto.FirestationResponseDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.StationNumberService;
import com.safetynet.safetynetalerts.service.StationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StationNumberServiceTest {
    @InjectMocks private StationNumberService stationNumberService;
    @Spy private DataRepository dataRepository = new DataRepository();

    private Person person;
    private Firestation fireStation;
    private MedicalRecord medicalRecord;

    public StationNumberServiceTest() throws Exception {}

    @BeforeEach
    public void setUpPerTest() throws IOException {
        person = new Person();
        fireStation = new Firestation();
        medicalRecord = new MedicalRecord();

        // Person mock set up
        person.setFirstName("john");
        person.setLastName("doe");
        person.setAddress("123 Avenue Foch");
        person.setCity("Paris");
        person.setEmail("john.doe@example.com");
        person.setZip("75025");
        person.setPhone("000-000-0000");

        // Fire Station mock set up
        fireStation.setAddress("123 Avenue Foch");
        fireStation.setStation("1");

        // Medical record mock set up
        medicalRecord.setFirstName("john");
        medicalRecord.setLastName("doe");
        medicalRecord.setBirthdate("01/01/2000");
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
    }

    @Test
    public void StationNumberTest() {
        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getFireStationList()).thenReturn(List.of(fireStation));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of(medicalRecord));

        FirestationResponseDTO response = stationNumberService.PersonByFireStation(1);

        assertNotNull(response);
        assertEquals(1, response.getAdultCount());
        assertEquals(0, response.getChildCount());
    }

    @Test
    public void StationNumberOnlyChildTest() {
        medicalRecord.setBirthdate("01/01/2020");

        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getFireStationList()).thenReturn(List.of(fireStation));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of(medicalRecord));

        FirestationResponseDTO response = stationNumberService.PersonByFireStation(1);

        assertNotNull(response);
        assertEquals(0, response.getAdultCount());
        assertEquals(1, response.getChildCount());
    }

    @Test
    public void StationNumberOrphanMedRecTest() {
        medicalRecord.setFirstName("Unknown");
        medicalRecord.setLastName("Person");
        medicalRecord.setBirthdate("01/01/1990");

        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getFireStationList()).thenReturn(List.of(fireStation));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of(medicalRecord));

        FirestationResponseDTO response = stationNumberService.PersonByFireStation(1);

        assertNotNull(response);
        assertEquals(0, response.getAdultCount());
        assertEquals(0, response.getChildCount());
    }

    @Test
    public void StationNumberUnknownStationTest() {
        fireStation.setStation("99");
        fireStation.setAddress("456 Unknown Street");

        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getFireStationList()).thenReturn(List.of(fireStation));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of(medicalRecord));

        FirestationResponseDTO response = stationNumberService.PersonByFireStation(1);

        assertNotNull(response);
        assertEquals(0, response.getAdultCount());
        assertEquals(0, response.getChildCount());
    }
}
