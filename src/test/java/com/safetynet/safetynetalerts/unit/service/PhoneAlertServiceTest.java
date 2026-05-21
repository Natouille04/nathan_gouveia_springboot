package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertServiceTest {
    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @Mock
    private DataRepository dataRepository;

    private Person person;
    private Firestation fireStation;

    @BeforeEach
    public void setUpPerTest() throws IOException {
        person = new Person();
        fireStation = new Firestation();

        person.setFirstName("john");
        person.setLastName("doe");
        person.setAddress("123 Avenue Foch");
        person.setCity("Paris");
        person.setEmail("john.doe@example.com");
        person.setZip("75025");
        person.setPhone("000-000-0000");

        fireStation.setAddress("123 Avenue Foch");
        fireStation.setStation("1");

        when(dataRepository.getPersonList()).thenReturn(List.of(person));
        when(dataRepository.getFireStationList()).thenReturn(List.of(fireStation));
    }

    @Test
    public void phoneAlertTest() {
        List<String> response = phoneAlertService.GetPhoneNumberByStation(1);

        verify(dataRepository).getPersonList();
        verify(dataRepository).getFireStationList();
        assertNotNull(response);
        assertEquals(person.getPhone(), response.get(0));
    }

    @Test
    public void phoneAlertFailTest() {
        List<String> response = phoneAlertService.GetPhoneNumberByStation(-1);
        assertTrue(response.isEmpty());
    }
}