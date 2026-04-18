package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneAlertService {
    final DataRepository dataRepository;

    public PhoneAlertService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<String> GetPhoneNumberByStation(int StationNumber) {
        // Initialisation de la réponse
        List<String> response = new ArrayList<>();

        // Récupérations des listes / Persons - FireStations
        List<Person> persons = dataRepository.getPersonList();
        List<Firestation> firestations = dataRepository.getFireStationList();

        // Filtrages des addresses selon le numéro de stations
        List<String> addresses = firestations.stream()
                .filter(s -> Integer.parseInt(s.getStation()) == StationNumber)
                .map(Firestation::getAddress)
                .toList();

        // Filtrages des individus habitant dans la zone de la caserne demandée et remplisage de la réponse avec les numéros de téléphones
        persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .forEach(p -> {
                    response.add(p.getPhone());
                });

        return response;
    }
}
