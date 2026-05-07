package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PersonsService {
    private DataRepository dataRepository;

    public PersonsService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public boolean save(Person person) {
        try {
            dataRepository.addPersonToList(person);
            return true;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Person update(String firstName, String lastName, PersonUpdateDTO dto) {
        try {
            dataRepository.updatePerson(firstName, lastName, dto);
            Person updatedPerson = dataRepository.getPersonList().stream()
                    .filter(p -> Objects.equals(firstName, p.getFirstName()) && Objects.equals(lastName, p.getLastName()))
                    .toList()
                    .get(0);

            return updatedPerson;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String firstName, String lastName) {
        try {
            dataRepository.deletePerson(firstName, lastName);
            return true;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
