package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneAlertService {
    final DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(PhoneAlertService.class);

    public PhoneAlertService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<String> GetPhoneNumberByStation(int StationNumber) {
        // Initialisation de la réponse
        List<String> response = new ArrayList<>();

        // Récupérations des listes / Persons - FireStations
        logger.debug("Parsing Persons and FireStations list");
        List<Person> persons = dataRepository.getPersonList();
        List<Firestation> firestations = dataRepository.getFireStationList();

        // Filtrages des addresses selon le numéro de station
        logger.debug("Filtering address with station number");
        List<String> addresses = firestations.stream()
                .filter(s -> Integer.parseInt(s.getStation()) == StationNumber)
                .map(Firestation::getAddress)
                .toList();

        // Filtrages des individus habitant dans la zone de la caserne demandée et remplisage de la réponse avec les numéros de téléphones
        logger.debug("Filtering persons with station number");
        persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .forEach(p -> {
                    response.add(p.getPhone());
                });

        logger.debug("Returning response");
        return response;
    }
}
