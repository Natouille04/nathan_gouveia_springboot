package com.safetynet.safetynetalerts.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Repository
public class DataRepository {
    private final List<Person> personList;
    private final List<Firestation> fireStationList;
    private final List<MedicalRecord> medicalRecordList;

    private static final Logger logger = LogManager.getLogger(DataRepository.class);

    public void saveToFile() throws IOException {
        logger.info("Saving data to file...");
        ObjectMapper mapper = new ObjectMapper();

        // On crée un objet qu'on injecte avec nos données modifiées
        Map<String, Object> data = new HashMap<>();
        data.put("persons", personList);
        data.put("firestations", fireStationList);
        data.put("medicalrecords", medicalRecordList);

        // On écrit les données dans un nouveau fichier (data-update.json).
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File("src/main/resources/data-update.json"), data);

        logger.info("Data saved");
    }

    public DataRepository() throws IOException {
        // On ouvre le fichier de données
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new File("src/main/resources/data.json"));


        // On assigne les données du fichier à des variables locales qu'on va pouvoir lire et modifiée
        this.personList = mapper.convertValue(json.get("persons"), new TypeReference<>() {});
        this.fireStationList = mapper.convertValue(json.get("firestations"), new TypeReference<>() {});
        this.medicalRecordList = mapper.convertValue(json.get("medicalrecords"), new TypeReference<>() {});
    }

    // --- READ functions --- //

    public List<Person> getPersonList() { return personList; }
    public List<Firestation> getFireStationList() { return fireStationList; }
    public List<MedicalRecord> getMedicalRecordList() { return medicalRecordList; }

    // --- CREATE functions --- //

    public void addPersonToList(Person person) throws IOException {
        // On vérifie que la personne n'éxiste pas déja
        boolean exists = personList.stream()
                .filter(p -> p.getFirstName().equals(person.getFirstName()))
                .anyMatch(p -> p.getLastName().equals(person.getLastName()));

        // Si ce n'est pas le cas, on l'ajoute au fichier
        if (!exists) {
            personList.add(person);

            try {
                saveToFile();
            }

            catch (IOException e) {
                throw new RuntimeException("Failed to persist data to file", e);
            }
        }

        // Sinon, on renvoie une erreur à l'utilisateur
        if (exists) {
            throw new IllegalArgumentException("Person already exists: "
                    + person.getFirstName() + " " + person.getLastName());
        }

    }


    public void addFirestationToList(Firestation firestation) throws IOException {
        // On vérifie que la station n'éxiste pas déja
        boolean exists = fireStationList.stream().anyMatch(s -> s.getAddress().equals(firestation.getAddress()));

        // Si ce n'est pas le cas, on l'ajoute au fichier
        if (!exists) {
            fireStationList.add(firestation);

            try {
                saveToFile();
            }

            catch (IOException e) {
                throw new RuntimeException("Failed to persist data to file", e);
            }
        }

        // Sinon, on renvoie une erreur à l'utilisateur
        if (exists) {
            throw new IllegalArgumentException("Station already exists: " + firestation.getAddress());
        }
    }

    public void addMedicalRecordToList(MedicalRecord medicalRecord) throws IOException {
        // On vérifie que le dossier médical n'éxiste pas déja
        boolean exists = medicalRecordList.stream()
                .filter(m -> m.getFirstName().equals(medicalRecord.getFirstName()))
                .anyMatch(m -> m.getLastName().equals(medicalRecord.getLastName()));

        // Si ce n'est pas le cas, on l'ajoute au fichier
        if (!exists) {
            medicalRecordList.add(medicalRecord);

            try {
                saveToFile();
            }

            catch (IOException e) {
                throw new RuntimeException("Failed to persist data to file", e);
            }
        }

        // Sinon, on renvoie une erreur à l'utilisateur
        if (exists) {
            throw new IllegalArgumentException("Medical Record already exists: " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
        }
    }

    // --- UPDATE functions --- //

    public void updatePerson(String firstName, String lastName, PersonUpdateDTO dto) {
        // On vérifie que la personne existe déja
        boolean exists = personList.stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .anyMatch(p -> p.getLastName().equals(lastName));

        // Si ce n'est pas le cas, on renvoie une erreur 404 à l'utilisateur
        if (!exists) {
            throw new RuntimeException("Person not found: " + firstName + " " + lastName);
        }

        // Sinon, on modifie les données demandées, puis on enregistre les modifications
        personList.forEach(p -> {
            if (Objects.equals(p.getFirstName(), firstName)) {
                if (Objects.equals(p.getLastName(), lastName)) {
                    if (dto.getAddress() != null) p.setAddress(dto.getAddress());
                    if (dto.getCity() != null) p.setCity(dto.getCity());
                    if (dto.getZip() != null) p.setZip(dto.getZip());
                    if (dto.getPhone() != null) p.setPhone(dto.getPhone());
                    if (dto.getEmail() != null) p.setEmail(dto.getEmail());
                }
            }
        });

        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }

    public void updateFireStation(String address, FireStationUpdateDTO firestation) {
        // On vérifie que la station existe déja
        boolean exists = fireStationList.stream().anyMatch(f ->
                f.getAddress().equals(address)
        );

        // Si ce n'est pas le cas, on renvoie une erreur 404 à l'utilisateur
        if (!exists) {
            throw new RuntimeException("Address not found: " + address);
        }

        // Sinon, on modifie les données demandées, puis on enregistre les modifications
        fireStationList.forEach(f -> {
            if (Objects.equals(f.getAddress(), address)) {
                if (firestation.getAddress() != null) f.setAddress(firestation.getAddress());
                if (firestation.getStation() != null) f.setStation(firestation.getStation());
            }
        });

        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }

    public void updateMedicalRecord(String firstName, String lastName, MedicalUpdateDTO dto) {
        // On vérifie que le dossier médical existe déja
        boolean exists = medicalRecordList.stream()
                .filter(m -> m.getFirstName().equals(firstName))
                .anyMatch(m -> m.getLastName().equals(lastName));

        // Si ce n'est pas le cas, on renvoie une erreur 404 à l'utilisateur
        if (!exists) {
            throw new RuntimeException("Person not found: " + firstName + " " + lastName);
        }

        // Sinon, on modifie les données demandées, puis on enregistre les modifications
        medicalRecordList.forEach(m -> {
            if (Objects.equals(m.getFirstName(), firstName))
                if (Objects.equals(m.getLastName(), lastName)) {
                    if (dto.getBirthdate() != null) m.setBirthdate(dto.getBirthdate());
                    if (dto.getMedications() != null) m.setMedications(dto.getMedications());
                    if (dto.getAllergies() != null) m.setAllergies(dto.getAllergies());
                }
        });

        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }

    // --- DELETE functions --- //

    public void deletePerson(String firstName, String lastName) {
        // On récupère l'index de la personne en vérifiant qu'elle existe
        int index = IntStream.range(0, personList.size())
                .filter(i -> personList.get(i).getFirstName().equals(firstName))
                .filter(i -> personList.get(i).getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Person not found: " + firstName + " " + lastName)); // Sinon, on renvoie une erreur 404.

        // On supprime la personne demandée selon son index
        personList.remove(index);

        // Puis, on enregistre les modifications dans le fichier
        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }

    public void deleteFireStation(String address) {
        // On récupère l'index de la station en vérifiant qu'elle existe
        int index = IntStream.range(0, fireStationList.size())
                .filter(i -> fireStationList.get(i).getAddress().equals(address))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Fire Station not found: " + address)); // Sinon, on renvoie une erreur 404.

        // On supprime la station demandée selon son index
        fireStationList.remove(index);

        // Puis, on enregistre les modifications dans le fichier
        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        // On récupère l'index de la station en vérifiant qu'elle existe
        int index = IntStream.range(0, medicalRecordList.size())
                .filter(i -> medicalRecordList.get(i).getFirstName().equals(firstName))
                .filter(i -> medicalRecordList.get(i).getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Medical Record not found: " + firstName + " " + lastName)); // Sinon, on renvoie une erreur 404.

        // On supprime la station demandée selon son index
        medicalRecordList.remove(index);

        // Puis, on enregistre les modifications dans le fichier
        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }
} 