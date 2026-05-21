package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonsServiceTest {
    @InjectMocks private PersonsService personsService;
    @Mock private DataRepository dataRepository;

    private Person person;

    @BeforeEach
    public void setUpPerTest() {
        person = new Person();
        person.setFirstName("john");
        person.setLastName("doe");
        person.setAddress("123 Avenue Foch");
        person.setCity("Paris");
        person.setEmail("john.doe@example.com");
        person.setZip("75025");
        person.setPhone("000-000-0000");
    }

    @Test
    public void savePersonTest() throws IOException {
        boolean response = personsService.save(person);

        assertTrue(response);
        verify(dataRepository, times(1)).addPersonToList(person);
    }

    @Test
    public void savePersonFailTest() throws IOException {
        doThrow(new RuntimeException("Person already exists"))
                .when(dataRepository).addPersonToList(person);

        boolean response = personsService.save(person);
        assertFalse(response);
    }

    @Test
    public void updatePersonTest() {
        PersonUpdateDTO dto = new PersonUpdateDTO();
        dto.setEmail("newemail@example.com");
        dto.setCity("London");
        dto.setZip("75000");
        dto.setPhone("123-456-7890");

        when(dataRepository.getPersonList()).thenReturn(new ArrayList<>(List.of(person)));

        doAnswer(invocation -> {
            person.setEmail(dto.getEmail());
            person.setCity(dto.getCity());
            person.setZip(dto.getZip());
            person.setPhone(dto.getPhone());
            return null;
        }).when(dataRepository).updatePerson("john", "doe", dto);

        Person response = personsService.update("john", "doe", dto);

        assertNotNull(response);
        verify(dataRepository, times(1)).updatePerson("john", "doe", dto);
        assertEquals("newemail@example.com", response.getEmail());
        assertEquals("London", response.getCity());
        assertEquals("75000", response.getZip());
        assertEquals("123-456-7890", response.getPhone());
    }

    @Test
    public void updatePersonFailTest() {
        PersonUpdateDTO dto = new PersonUpdateDTO();
        assertThrows(RuntimeException.class, () -> personsService.update("James", "Kirk", dto));
    }

    @Test
    public void deletePersonTest() {
        when(dataRepository.getPersonList()).thenReturn(new ArrayList<>(List.of(person)));
        doAnswer(invocation -> {
            dataRepository.getPersonList().remove(person);
            return null;
        }).when(dataRepository).deletePerson("john", "doe");

        boolean response = personsService.delete("john", "doe");
        assertTrue(response);
    }

    @Test
    public void deletePersonFailTest() {
        doThrow(new RuntimeException("Person not found: James Kirk"))
                .when(dataRepository).deletePerson("James", "Kirk");

        assertThrows(RuntimeException.class, () -> personsService.delete("James", "Kirk"));
    }
}