package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PersonsService {
    private final DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(PersonsService.class);

    public PersonsService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public boolean save(Person person) {
        // On essaye d'enregistrer la personne avec le dataRepository
        try {
            logger.debug("Saving person to list...");
            dataRepository.addPersonToList(person);
            logger.debug("Saved !!!");
            return true;
        }

        // S'il y a une erreur, on renvoie false
        catch (Exception e) {
            logger.error("Error while saving person");
            return false;
        }
    }

    public Person update(String firstName, String lastName, PersonUpdateDTO dto) {
        // On essaye de modifier la personne avec le dataRepository
        try {
            logger.debug("Updating person with first name '{}' and last name '{}'", firstName, lastName);
            dataRepository.updatePerson(firstName, lastName, dto);

            return dataRepository.getPersonList().stream()
                    .filter(p -> Objects.equals(firstName, p.getFirstName()))
                    .filter(p -> Objects.equals(lastName, p.getLastName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Person not found after update: " + firstName + " " + lastName));
        }

        // S'il y a une erreur, on renvoie une Runtime Exception
        catch (Exception e) {
            logger.error("Error while updating person");
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String firstName, String lastName) {
        // On essaye de supprimer la personne avec le dataRepository
        try {
            logger.debug("Deleting person with first name '{}' and last name '{}'", firstName, lastName);
            dataRepository.deletePerson(firstName, lastName);

            logger.debug("Deleted !!!");
            return true;
        }

        // S'il y a une erreur, on renvoie une Runtime Exception
        catch (Exception e) {
            logger.error("Error while deleting person");
            throw new RuntimeException(e);
        }
    }
}