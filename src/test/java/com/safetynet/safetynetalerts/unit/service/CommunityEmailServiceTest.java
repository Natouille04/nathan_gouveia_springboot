package com.safetynet.safetynetalerts.unit.service;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.CommunityEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {
    @InjectMocks private CommunityEmailService communityEmailService;
    @Spy private DataRepository dataRepository = new DataRepository();

    private Person person;

    public CommunityEmailServiceTest() throws Exception {}

    @BeforeEach
    public void setUpPerTest() throws IOException {
        person = new Person();

        // Person mock set up
        person.setFirstName("john");
        person.setLastName("doe");
        person.setAddress("123 Avenue Foch");
        person.setCity("Paris");
        person.setEmail("john.doe@example.com");
        person.setZip("75025");
        person.setPhone("000-000-0000");

        when(dataRepository.getPersonList()).thenReturn(List.of(person));
    }

    @Test
    public void CommunityEmailTest() {
        List<String> response = communityEmailService.GetEmailsByCity("Paris");
        assertFalse(response.isEmpty());
    }

    @Test
    public void CommunityEmailFailTest() {
        List<String> response = communityEmailService.GetEmailsByCity("London");
        assertTrue(response.isEmpty());
    }
}
