package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.PersonInfoLastNameController;
import com.safetynet.safetynetalerts.dto.PersonInfoLastNameResponseDTO;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.PersonInfoLastNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonInfoLastNameControllerTestIT {
    private PersonInfoLastNameController personInfoLastNameController;

    @BeforeEach
    public void setUp() throws IOException {
        DataRepository dataRepository = new DataRepository();
        PersonInfoLastNameService personInfoLastNameService = new PersonInfoLastNameService(dataRepository);
        personInfoLastNameController = new PersonInfoLastNameController(personInfoLastNameService);
    }

    @Test
    public void personByLastNameTest() {
        List<PersonInfoLastNameResponseDTO> response = personInfoLastNameController.personInfoLastName("Boyd");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
