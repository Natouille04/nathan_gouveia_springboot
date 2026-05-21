package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.controller.ChildAlertController;
import com.safetynet.safetynetalerts.controller.CommunityEmailController;
import com.safetynet.safetynetalerts.dto.ChildAlertResponseDTO;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import com.safetynet.safetynetalerts.service.CommunityEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommunityEmailTestIT {
    private CommunityEmailController communityEmailController;

    @BeforeEach
    public void setUp() throws IOException {
        DataRepository dataRepository = new DataRepository();
        CommunityEmailService communityEmailService = new CommunityEmailService(dataRepository);
        communityEmailController = new CommunityEmailController(communityEmailService);
    }

    @Test
    public void getAllEmailFromCity() {
        List<String> response = communityEmailController.communityEmail("Culver");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
