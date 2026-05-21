package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.ChildAlertController;
import com.safetynet.safetynetalerts.dto.ChildAlertResponseDTO;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChildAlertControllerTestIT {
    private ChildAlertController childAlertController;

    @BeforeEach
    public void setUp() throws IOException {
        DataRepository dataRepository = new DataRepository();
        ChildAlertService childAlertService = new ChildAlertService(dataRepository);
        childAlertController = new ChildAlertController(childAlertService);
    }

    @Test
    public void getAllChildFromAddressTest() {
        List<ChildAlertResponseDTO> response = childAlertController.childAlert("1509 Culver St");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
