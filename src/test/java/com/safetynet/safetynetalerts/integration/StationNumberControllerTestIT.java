package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.CommunityEmailController;
import com.safetynet.safetynetalerts.controller.StationNumberController;
import com.safetynet.safetynetalerts.dto.FirestationResponseDTO;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.CommunityEmailService;
import com.safetynet.safetynetalerts.service.StationNumberService;
import com.safetynet.safetynetalerts.unit.service.StationsServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StationNumberControllerTestIT {
    private StationNumberController stationNumberController;

    @BeforeEach
    public void setUp() throws IOException {
        DataRepository dataRepository = new DataRepository();
        StationNumberService stationNumberService = new StationNumberService(dataRepository);
        stationNumberController = new StationNumberController(stationNumberService);
    }

    @Test
    public void getPersonsWithFireStation() {
        FirestationResponseDTO response = stationNumberController.firestation(1);

        assertNotNull(response);
        assertFalse(response.getPersons().isEmpty());
    }
}
