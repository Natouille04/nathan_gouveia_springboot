package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @InjectMocks private MedicalRecordService medicalRecordService;
    @Mock private DataRepository dataRepository;

    private MedicalRecord medicalRecord;

    @BeforeEach
    public void setUpPerTest() {
        medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("john");
        medicalRecord.setLastName("doe");
        medicalRecord.setBirthdate("01/01/2000");
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
    }

    @Test
    public void saveMedicalRecordTest() throws IOException {
        boolean response = medicalRecordService.save(medicalRecord);

        assertTrue(response);
        verify(dataRepository, times(1)).addMedicalRecordToList(medicalRecord);
    }

    @Test
    public void saveMedicalRecordFailTest() throws IOException {
        doThrow(new RuntimeException("Person already exists"))
                .when(dataRepository).addMedicalRecordToList(medicalRecord);

        boolean response = medicalRecordService.save(medicalRecord);

        assertFalse(response);
    }

    @Test
    public void updateMedicalRecordTest() {
        MedicalUpdateDTO dto = new MedicalUpdateDTO();
        dto.setBirthdate("01/01/2000");
        dto.setMedications(new ArrayList<>());
        dto.setAllergies(new ArrayList<>());

        when(dataRepository.getMedicalRecordList()).thenReturn(new ArrayList<>(List.of(medicalRecord)));

        doAnswer(invocation -> {
            medicalRecord.setBirthdate(dto.getBirthdate());
            medicalRecord.setMedications(dto.getMedications());
            medicalRecord.setAllergies(dto.getAllergies());
            return null;
        }).when(dataRepository).updateMedicalRecord("john", "doe", dto);

        MedicalRecord response = medicalRecordService.update("john", "doe", dto);

        assertNotNull(response);
        verify(dataRepository, times(1)).updateMedicalRecord("john", "doe", dto);
        assertEquals("01/01/2000", response.getBirthdate());
        assertEquals(new ArrayList<>(), response.getMedications());
        assertEquals(new ArrayList<>(), response.getAllergies());
    }

    @Test
    public void updateMedicalRecordFailTest() {
        MedicalUpdateDTO dto = new MedicalUpdateDTO();
        when(dataRepository.getMedicalRecordList()).thenReturn(new ArrayList<>());

        assertThrows(RuntimeException.class, () -> medicalRecordService.update("James", "Kirk", dto));
    }

    @Test
    public void deleteMedicalRecordTest() {
        when(dataRepository.getMedicalRecordList()).thenReturn(new ArrayList<>(List.of(medicalRecord)));
        doAnswer(invocation -> {
            dataRepository.getMedicalRecordList().remove(medicalRecord);
            return null;
        }).when(dataRepository).deleteMedicalRecord("john", "doe");

        boolean response = medicalRecordService.delete("john", "doe");
        assertTrue(response);
    }

    @Test
    public void deleteMedicalRecordFailTest() {
        doThrow(new RuntimeException("Person not found: James Kirk"))
                .when(dataRepository).deleteMedicalRecord("James", "Kirk");

        assertThrows(RuntimeException.class, () -> medicalRecordService.delete("James", "Kirk"));
    }
}
