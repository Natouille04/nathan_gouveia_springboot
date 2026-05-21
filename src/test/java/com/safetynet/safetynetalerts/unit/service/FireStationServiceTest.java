package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.FireService;
import com.safetynet.safetynetalerts.service.FireStationsService;
import com.safetynet.safetynetalerts.service.PersonsService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {
    @InjectMocks private FireStationsService fireStationsService;
    @Mock private DataRepository dataRepository;

    private Firestation firestation;

    @BeforeEach
    public void setUpPerTest() {
        firestation = new Firestation();
        firestation.setStation("1");
        firestation.setAddress("123 Avenue Foch");
    }

    @Test
    public void saveFireStationTest() throws IOException {
        boolean response = fireStationsService.save(firestation);

        assertTrue(response);
        verify(dataRepository, times(1)).addFirestationToList(firestation);
    }

    @Test
    public void saveFireStationFailTest() throws IOException {
        doThrow(new RuntimeException("Fire Station already exists"))
                .when(dataRepository).addFirestationToList(firestation);

        boolean response = fireStationsService.save(firestation);
        assertFalse(response);
    }

    @Test
    public void updateFireStationTest() {
        FireStationUpdateDTO dto = new FireStationUpdateDTO();
        dto.setStation("2");
        dto.setAddress("234 Rue Poch");

        when(dataRepository.getFireStationList()).thenReturn(new ArrayList<>(List.of(firestation)));

        doAnswer(invocation -> {
            firestation.setStation(dto.getStation());
            firestation.setAddress(dto.getAddress());
            return null;
        }).when(dataRepository).updateFireStation("123 Avenue Foch", dto);

        Firestation response = fireStationsService.update("123 Avenue Foch", dto);

        assertNotNull(response);
        verify(dataRepository, times(1)).updateFireStation("123 Avenue Foch", dto);
        assertEquals("2", response.getStation());
        assertEquals("234 Rue Poch", response.getAddress());
    }

    @Test
    public void updateFireStationFailTest() {
        FireStationUpdateDTO dto = new FireStationUpdateDTO();
        dto.setStation("2");
        dto.setAddress("234 Rue Poch");

        when(dataRepository.getFireStationList()).thenReturn(new ArrayList<>());

        assertThrows(RuntimeException.class, () -> fireStationsService.update("123 Avenue Foch", dto));
    }

    @Test
    public void deleteFireStationTest() {
        when(dataRepository.getFireStationList()).thenReturn(new ArrayList<>(List.of(firestation)));
        doAnswer(invocation -> {
            dataRepository.getFireStationList().remove(firestation);
            return null;
        }).when(dataRepository).deleteFireStation("234 Rue Poch");

        boolean response = fireStationsService.delete("234 Rue Poch");
        assertTrue(response);
    }

    @Test
    public void deleteFireStationFailTest() {
        doThrow(new RuntimeException("Address not found: 234 Rue Poch"))
            .when(dataRepository).deleteFireStation("234 Rue Poch");

        assertThrows(RuntimeException.class, () -> fireStationsService.delete("234 Rue Poch"));
    }
}
