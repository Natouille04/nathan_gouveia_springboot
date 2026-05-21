package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.PhoneAlertController;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PhoneAlertControllerTestIT {
    private PhoneAlertController phoneAlertController;

    @BeforeEach
    public void setUp() throws IOException {
        DataRepository dataRepository = new DataRepository();
        PhoneAlertService phoneAlertService = new PhoneAlertService(dataRepository);
        phoneAlertController = new PhoneAlertController(phoneAlertService);
    }

    @Test
    public void PhoneNumbersByFireStations() {
        List<String> response = phoneAlertController.phoneAlert(1);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
