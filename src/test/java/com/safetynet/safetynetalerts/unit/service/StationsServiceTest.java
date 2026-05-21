package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.dto.AddressDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StationsServiceTest {
    @InjectMocks private StationsService stationsService;
    @Spy private DataRepository dataRepository = new DataRepository();

    private Person person;
    private Firestation fireStation;
    private MedicalRecord medicalRecord;

    public StationsServiceTest() throws Exception {}

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
    public void addressByFireStationsTest() {
        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getFireStationList()).thenReturn(List.of(fireStation));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of(medicalRecord));

        List<AddressDTO> result = stationsService.AddressByFirestation(1);
        int age = result.get(0).getInhabitants().get(0).getAge();

        verify(dataRepository).getPersonList();
        verify(dataRepository).getFireStationList();
        verify(dataRepository).getMedicalRecordList();
        assertNotNull(result);
        assertEquals(fireStation.getAddress(), result.get(0).getAddress());
        assertEquals(26, age);
    }

    @Test
    public void addressByFireStationWithUnknownStation() {
        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getFireStationList()).thenReturn(List.of(fireStation));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of(medicalRecord));

        List<AddressDTO> result = stationsService.AddressByFirestation(99);

        // Retourne true //
        assertTrue(result.isEmpty());
    }
}
