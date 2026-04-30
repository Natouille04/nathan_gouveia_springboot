package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommunityEmailService {
    private final DataRepository dataRepository;
    public CommunityEmailService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<String> GetEmailsByCity(String city) {
        List<String> response = new ArrayList<>();
        List<Person> persons = dataRepository.getPersonList();

        persons.forEach(p -> {
            if(Objects.equals(p.getCity(), city)) {
                response.add(p.getEmail());
            }
        });

        return response;
    }
}
