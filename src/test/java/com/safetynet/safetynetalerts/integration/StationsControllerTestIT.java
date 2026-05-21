package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.PhoneAlertController;
import com.safetynet.safetynetalerts.controller.StationsController;
import com.safetynet.safetynetalerts.dto.AddressDTO;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.PhoneAlertService;
import com.safetynet.safetynetalerts.service.StationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StationsControllerTestIT {
    private StationsController stationsController;

    @BeforeEach
    public void setUp() throws IOException {
        DataRepository dataRepository = new DataRepository();
        StationsService stationsService = new StationsService(dataRepository);
        stationsController = new StationsController(stationsService);
    }

    @Test
    public void AddressByFireStations() {
        List<AddressDTO> response = stationsController.stations(1);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
