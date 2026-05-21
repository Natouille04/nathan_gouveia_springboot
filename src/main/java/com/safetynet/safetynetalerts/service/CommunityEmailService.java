package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommunityEmailService {
    private final DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(CommunityEmailService.class);

    public CommunityEmailService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<String> GetEmailsByCity(String city) {
        List<String> response = new ArrayList<>();

        logger.debug("Parsing Persons list");
        List<Person> persons = dataRepository.getPersonList();

        logger.debug("Parsing emails");
        persons.forEach(p -> {
            if(Objects.equals(p.getCity(), city)) {
                response.add(p.getEmail());
            }
        });

        logger.debug("Returning email list");
        return response;
    }
}
