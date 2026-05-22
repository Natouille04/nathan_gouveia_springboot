package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.AddressDTO;
import com.safetynet.safetynetalerts.dto.InhabitantDTO;
import com.safetynet.safetynetalerts.dto.MedRecDTO;
import com.safetynet.safetynetalerts.dto.PersonMedInfoDTO;
import com.safetynet.safetynetalerts.model.Firestation;
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

import static java.lang.Integer.parseInt;

@Service
public class StationsService {
    private final DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(StationsService.class);

    public StationsService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<AddressDTO> AddressByFirestation(int StationNumber) {
        // On initialise la liste qui contiendra notre réponse
        List<AddressDTO> response = new ArrayList<>();

        logger.debug("Defining DateTime formatter and today's date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();

        logger.debug("Parsing Persons, FireStations and MedicalRecords list");
        List<Person> Persons = dataRepository.getPersonList();
        List<Firestation> FireStations = dataRepository.getFireStationList();
        List<MedicalRecord> MedicalRecords = dataRepository.getMedicalRecordList();

        logger.debug("Getting Fire Station using chosen station number");
        List<Firestation> filteredFireStations = FireStations.stream().filter(s -> Objects.equals(StationNumber, parseInt(s.getStation()))).toList();

        // On extrait la liste des adresses uniques desservies par ces casernes
        logger.debug("Parsing only address from fire stations");
        List<String> filteredAddresses = filteredFireStations.stream()
                .map(Firestation::getAddress)
                .toList();

        // On récupère toutes les personnes qui habitent aux adresses identifiées
        logger.debug("Filtering list with list of address");
        List<Person> filteredPersons = Persons.stream()
                .filter(p -> filteredAddresses.contains(p.getAddress()))
                .toList();

        // On parcourt chaque adresse pour y associer ses habitants
        logger.debug("Filling response with address and address inhabitant");
        filteredAddresses.forEach(address -> {
            AddressDTO addressDTO = new AddressDTO();
            List<InhabitantDTO> inhabitants = new ArrayList<>();

            // On filtre les personnes pour ne garder que celles qui vivent à l'adresse courante
            List<Person> personsAtAddress = filteredPersons.stream().filter(p -> Objects.equals(p.getAddress(), address)).toList();

            // On construit le profil complet de chaque habitant à cette adresse
            personsAtAddress.forEach(p -> {
                InhabitantDTO inhabitant = new InhabitantDTO();
                MedRecDTO MedRec = new MedRecDTO();

                // On récupère le dossier médical correspondant au prénom et au nom de l'habitant
                MedicalRecord MedicalRecord = MedicalRecords.stream()
                        .filter(m -> Objects.equals(m.getFirstName(), p.getFirstName()))
                        .filter(m -> Objects.equals(m.getLastName(), p.getLastName()))
                        .toList()
                        .get(0);

                // On remplit les informations médicales (médicaments et allergies)
                MedRec.setMedications(MedicalRecord.getMedications());
                MedRec.setAllergies(MedicalRecord.getAllergies());

                // On mappe les données de l'habitant et son âge calculé
                inhabitant.setFirstName(p.getFirstName());
                inhabitant.setLastName(p.getLastName());
                inhabitant.setAge(Period.between(LocalDate.parse(MedicalRecord.getBirthdate(), formatter), now).getYears());
                inhabitant.setPhone(p.getPhone());
                inhabitant.setMedicalRecord(MedRec);

                // On ajoute l'habitant à la liste de l'adresse
                inhabitants.add(inhabitant);
            });

            addressDTO.setAddress(address);
            addressDTO.setInhabitants(inhabitants);

            // On ajoute le DTO de l'adresse complet à notre liste de réponse
            response.add(addressDTO);
        });

        // On retourne la liste finale
        logger.debug("Returning response");
        return response;
    }
}