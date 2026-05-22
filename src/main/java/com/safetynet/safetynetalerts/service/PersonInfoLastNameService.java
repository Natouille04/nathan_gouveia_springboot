package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MedRecDTO;
import com.safetynet.safetynetalerts.dto.PersonInfoLastNameResponseDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PersonInfoLastNameService {
    private final DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(PersonInfoLastNameService.class);

    public PersonInfoLastNameService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<PersonInfoLastNameResponseDTO> GetPersonByLastName(String LastName) {
        // On initialise la liste qui contiendra notre réponse
        List<PersonInfoLastNameResponseDTO> response = new ArrayList<>();

        logger.debug("Parsing Persons, FireStations and MedicalRecords list");
        List<Person> persons = this.dataRepository.getPersonList();
        List<MedicalRecord> medicalRecords = this.dataRepository.getMedicalRecordList();

        logger.debug("Defining DateTime formatter and today's date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();

        // On filtre la liste des personnes pour ne garder que celles qui portent le nom recherché
        List<Person> personsFromFamilies = persons.stream().filter(p -> Objects.equals(p.getLastName(), LastName)).toList();

        // On parcourt chaque personne trouvée pour construire son profil complet
        personsFromFamilies.forEach(p -> {
            PersonInfoLastNameResponseDTO personInfo = new PersonInfoLastNameResponseDTO();
            MedRecDTO medRec = new MedRecDTO();

            // On récupère le dossier médical correspondant au prénom et nom de la personne
            MedicalRecord medicalRecord = medicalRecords.stream()
                    .filter(m -> Objects.equals(m.getFirstName(), p.getFirstName()))
                    .filter(m -> Objects.equals(m.getLastName(), p.getLastName()))
                    .toList()
                    .get(0);

            // On remplit les informations médicales (médicaments et allergies)
            medRec.setMedications(medicalRecord.getMedications());
            medRec.setAllergies(medicalRecord.getAllergies());

            // On mappe les données de la personne et son âge calculé vers le DTO
            personInfo.setFirstName(p.getFirstName());
            personInfo.setLastName(p.getLastName());
            personInfo.setAddress(p.getAddress());
            personInfo.setAge(Period.between(LocalDate.parse(medicalRecord.getBirthdate(), formatter), now).getYears());
            personInfo.setEmail(p.getEmail());
            personInfo.setMedicalRecord(medRec);

            // On ajoute le DTO complet à notre liste de réponse
            response.add(personInfo);
        });

        // On retourne la liste finale
        logger.debug("Returning response");
        return response;
    }
}