package com.safetynet.safetynetalerts.unit.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DataRepositoryTest {
    @Spy private DataRepository dataRepository = new DataRepository();

    @Mock private Person personMock;
    @Mock private Firestation firestationMock;
    @Mock private MedicalRecord medicalRecordMock;

    public DataRepositoryTest() throws IOException {}

    @BeforeEach
    public void setUpPerTest() throws IOException {
        // Person mock set up
        personMock.setFirstName("john");
        personMock.setLastName("doe");
        personMock.setAddress("123 Avenue Foch");
        personMock.setCity("Paris");
        personMock.setEmail("john.doe@example.com");
        personMock.setZip("75025");
        personMock.setPhone("000-000-0000");

        // Fire Station mock set up
        firestationMock.setAddress("123 Avenue Foch");
        firestationMock.setStation("1");

        // Medical record mock set up
        medicalRecordMock.setFirstName("john");
        medicalRecordMock.setLastName("doe");
        medicalRecordMock.setBirthdate("01/01/2000");
        medicalRecordMock.setMedications(new ArrayList<>());
        medicalRecordMock.setAllergies(new ArrayList<>());
    }

    @Test
    public void getPersonsTest() {
        List<Person> persons = dataRepository.getPersonList();
        assertNotNull(persons);
    }

    @Test
    public void getFireStationsTest() {
        List<Firestation> firestations = dataRepository.getFireStationList();
        assertNotNull(firestations);
    }

    @Test
    public void getMedicalRecordTest() {
        List<MedicalRecord> medicalRecords = dataRepository.getMedicalRecordList();
        assertNotNull(medicalRecords);
    }

    @Test
    public void createPersonTest() throws Exception {
        dataRepository.addPersonToList(personMock);
        verify(dataRepository, times(1)).saveToFile();
    }

    @Test
    public void createFireStationTest() throws Exception {
        dataRepository.addFirestationToList(firestationMock);
        verify(dataRepository, times(1)).saveToFile();
    }

    @Test
    public void createMedicalRecordTest() throws Exception {
        dataRepository.addMedicalRecordToList(medicalRecordMock);
        verify(dataRepository, times(1)).saveToFile();
    }
}
