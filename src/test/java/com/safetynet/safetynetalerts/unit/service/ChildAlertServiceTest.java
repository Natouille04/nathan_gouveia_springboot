package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.dto.ChildAlertResponseDTO;
import com.safetynet.safetynetalerts.dto.PersonMedInfoDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import com.safetynet.safetynetalerts.service.FireService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChildAlertServiceTest {
    @InjectMocks private ChildAlertService childAlertService;
    @Spy private DataRepository dataRepository = new DataRepository();

    public ChildAlertServiceTest() throws IOException {}

    private Person person;
    private Person otherPerson;
    private MedicalRecord medicalRecord;
    private MedicalRecord otherMedicalRecord;

    @BeforeEach
    public void setUpPerTest() throws IOException {
        person = new Person();
        medicalRecord = new MedicalRecord();

        otherPerson = new Person();
        otherMedicalRecord = new MedicalRecord();

        // Person mock set up
        person.setFirstName("john");
        person.setLastName("doe");
        person.setAddress("123 Avenue Foch");
        person.setCity("Paris");
        person.setEmail("john.doe@example.com");
        person.setZip("75025");
        person.setPhone("000-000-0000");

        // Household mock setup
        otherPerson.setFirstName("Gary");
        otherPerson.setLastName("Bourdat");
        otherPerson.setAddress("123 Avenue Foch");
        otherPerson.setCity("Paris");
        otherPerson.setZip("75025");
        otherPerson.setPhone("000-000-0000");

        // Medical record mock set up
        medicalRecord.setFirstName("john");
        medicalRecord.setLastName("doe");
        medicalRecord.setBirthdate("01/01/2010");
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());

        // Household MedRec mock setup
        otherMedicalRecord.setFirstName("Gary");
        otherMedicalRecord.setLastName("Bourdat");
        otherMedicalRecord.setBirthdate("01/01/2000");
        otherMedicalRecord.setMedications(new ArrayList<>());
        otherMedicalRecord.setAllergies(new ArrayList<>());
    }

    @Test
    public void childAlertTest() {
        when(dataRepository.getPersonList()).thenReturn(List.of(person, otherPerson));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of(medicalRecord, otherMedicalRecord));

        List<ChildAlertResponseDTO> response = childAlertService.GetChildrenByAddress("123 Avenue Foch");

        assertNotNull(response);
        assertEquals(1, response.size());

        ChildAlertResponseDTO childInfo = response.get(0);
        assertEquals("john", childInfo.getFirstName());
        assertEquals("doe", childInfo.getLastName());
        assertNotNull(childInfo.getHouseHoldMember());
        assertEquals(1, childInfo.getHouseHoldMember().size());
        assertEquals("Gary", childInfo.getHouseHoldMember().get(0).getFirstName());
        assertEquals("Bourdat", childInfo.getHouseHoldMember().get(0).getLastName());
    }

    @Test
    public void childAlertEmptyListTest() {
        when(dataRepository.getPersonList()).thenReturn(List.of());
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of());

        List<ChildAlertResponseDTO> response = childAlertService.GetChildrenByAddress("123 Avenue Foch");
        assertTrue(response.isEmpty());
    }

    @Test
    public void childAlertEmptyMedRecTest() {
        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of());

        List<ChildAlertResponseDTO> response = childAlertService.GetChildrenByAddress("123 Avenue Foch");
        assertTrue(response.isEmpty());
    }
}
