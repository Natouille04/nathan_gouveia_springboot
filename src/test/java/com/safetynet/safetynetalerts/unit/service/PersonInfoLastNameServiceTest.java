package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.dto.PersonInfoLastNameResponseDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import com.safetynet.safetynetalerts.service.PersonInfoLastNameService;
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
public class PersonInfoLastNameServiceTest {
    @InjectMocks private PersonInfoLastNameService personInfoLastNameService;
    @Spy private DataRepository dataRepository = new DataRepository();

    public PersonInfoLastNameServiceTest() throws IOException {}

    private Person person;
    private MedicalRecord medicalRecord;

    @BeforeEach
    public void setUpPerTest() throws IOException {
        person = new Person();
        medicalRecord = new MedicalRecord();

        // Person mock set up
        person.setFirstName("john");
        person.setLastName("doe");
        person.setAddress("123 Avenue Foch");
        person.setCity("Paris");
        person.setEmail("john.doe@example.com");
        person.setZip("75025");
        person.setPhone("000-000-0000");

        // Medical record mock set up
        medicalRecord.setFirstName("john");
        medicalRecord.setLastName("doe");
        medicalRecord.setBirthdate("01/01/2010");
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());

        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getMedicalRecordList()).thenReturn(List.of(medicalRecord));
    }

    @Test
    public void personInfoLastNameTest() {
        List<PersonInfoLastNameResponseDTO> response = personInfoLastNameService.GetPersonByLastName("doe");

        assertNotNull(response);
        assertEquals(1, response.size());
    }
}
