package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FirestationResponseDTO;
import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StationNumberService {
    private final DataRepository dataRepository;

    public StationNumberService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public FirestationResponseDTO PersonByFireStation(int FireStationNumber) {
        List<Person> persons = dataRepository.getPersonList();
        List<Firestation> firestation = dataRepository.getFireStationList();
        List<MedicalRecord> medicalRecord = dataRepository.getMedicalRecordList();

        // Déclaration des variables : DTO, liste de personnes, formateur de date, compteurs adultes/enfants
        FirestationResponseDTO FirestationDTO = new FirestationResponseDTO();
        List<PersonInfoDTO> PersonDTOList = new java.util.ArrayList<>(List.of());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        AtomicInteger adultCount = new AtomicInteger();
        AtomicInteger childCount = new AtomicInteger();

        // Filtrage des adresses couvertes par la caserne et des personnes résidant à ces adresses
        List<String> addresses = firestation.stream()
                .filter(s -> Integer.parseInt(s.getStation()) == FireStationNumber)
                .map(Firestation::getAddress)
                .toList();

        List<Person> filteredPersons = persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();

        // Comptage des adultes et des enfants via les dossiers médicaux
        medicalRecord.stream()
                .filter(m -> filteredPersons.stream()
                        .anyMatch(p -> p.getFirstName().equals(m.getFirstName())
                                && p.getLastName().equals(m.getLastName())))
                .forEach(p -> {
                    LocalDate birthdate = LocalDate.parse(p.getBirthdate(), formatter);
                    LocalDate now = LocalDate.now();

                    int age = Period.between(birthdate, now).getYears();

                    if (age <= 18) {
                        childCount.getAndIncrement();
                    }

                    else {
                        adultCount.getAndIncrement();
                    }
                });

        // Construction de la liste des personnes avec leurs informations
        filteredPersons.forEach(p -> {
            PersonInfoDTO personDTO = new PersonInfoDTO();

            personDTO.setFirstName(p.getFirstName());
            personDTO.setLastName(p.getLastName());
            personDTO.setAddress(p.getAddress());
            personDTO.setPhone(p.getPhone());

            PersonDTOList.add(personDTO);
        });

        // Définition des variables de FirestationDTO
        FirestationDTO.setPersons(PersonDTOList);
        FirestationDTO.setChildCount(childCount.intValue());
        FirestationDTO.setAdultCount(adultCount.intValue());

        // Renvoie le DTO
        return FirestationDTO;
    }
}