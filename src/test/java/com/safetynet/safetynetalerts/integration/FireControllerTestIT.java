package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.ChildAlertController;
import com.safetynet.safetynetalerts.controller.FireController;
import com.safetynet.safetynetalerts.dto.ChildAlertResponseDTO;
import com.safetynet.safetynetalerts.dto.FireResponseDTO;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import com.safetynet.safetynetalerts.service.FireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FireControllerTestIT {
    private FireController fireController;

    @BeforeEach
    public void setUp() throws IOException {
        DataRepository dataRepository = new DataRepository();
        FireService fireService = new FireService(dataRepository);
        fireController = new FireController(fireService);
    }

    @Test
    public void getAllPersonsFromAddressTest() {
        FireResponseDTO response = fireController.fire("1509 Culver St");

        assertNotNull(response);
        assertFalse(response.getPersonsMedInfos().isEmpty());
    }
}
