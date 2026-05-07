package com.safetynet.safetynetalerts.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
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

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();
        data.put("persons", personList);
        data.put("firestations", fireStationList);
        data.put("medicalrecords", medicalRecordList);

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File("src/main/resources/data-update.json"), data);


    }

    public DataRepository() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new File("src/main/resources/data.json"));

        this.personList = mapper.convertValue(json.get("persons"), new TypeReference<>() {});
        this.fireStationList = mapper.convertValue(json.get("firestations"), new TypeReference<>() {});
        this.medicalRecordList = mapper.convertValue(json.get("medicalrecords"), new TypeReference<>() {});
    }

    // --- READ functions --- //

    public List<Person> getPersonList() { return personList; }
    public List<Firestation> getFireStationList() { return fireStationList; }
    public List<MedicalRecord> getMedicalRecordList() { return medicalRecordList; }

    // --- CREATE functions --- //

    public void addPersonToList(Person person) {
        boolean exists = personList.stream().anyMatch(p ->
                p.getFirstName().equals(person.getFirstName()) &&
                p.getLastName().equals(person.getLastName())
        );

        if (!exists) {
            personList.add(person);

            try {
                saveToFile();
            }

            catch (IOException e) {
                throw new RuntimeException("Failed to persist data to file", e);
            }
        }
    }

    public void addFirestationToList(Firestation firestation) {
        boolean exists = fireStationList.stream().anyMatch(s -> s.getAddress().equals(firestation.getAddress()));

        if (!exists) {
            fireStationList.add(firestation);

            try {
                saveToFile();
            }

            catch (IOException e) {
                throw new RuntimeException("Failed to persist data to file", e);
            }
        }
    }

    public void addMedicalRecordToList(MedicalRecord medicalRecord) {
        boolean exists = medicalRecordList.stream().anyMatch(m ->
                m.getFirstName().equals(medicalRecord.getFirstName()) &&
                m.getLastName().equals(medicalRecord.getLastName())
        );

        if (!exists) {
            medicalRecordList.add(medicalRecord);

            try {
                saveToFile();
            }

            catch (IOException e) {
                throw new RuntimeException("Failed to persist data to file", e);
            }
        }
    }

    // --- UPDATE functions --- //

    public void updatePerson(String firstName, String lastName, PersonUpdateDTO dto) {
        boolean exists = personList.stream().anyMatch(p ->
                p.getFirstName().equals(firstName) &&
                        p.getLastName().equals(lastName)
        );

        if (!exists) {
            throw new RuntimeException("Person not found: " + firstName + " " + lastName);
        }

        personList.forEach(p -> {
            if (Objects.equals(p.getFirstName(), firstName) && Objects.equals(p.getLastName(), lastName)) {
                if (dto.getAddress() != null) p.setAddress(dto.getAddress());
                if (dto.getCity()    != null) p.setCity(dto.getCity());
                if (dto.getZip()     != null) p.setZip(dto.getZip());
                if (dto.getPhone()   != null) p.setPhone(dto.getPhone());
                if (dto.getEmail()   != null) p.setEmail(dto.getEmail());
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
        boolean exists = fireStationList.stream().anyMatch(f ->
                f.getAddress().equals(address)
        );

        if (!exists) {
            throw new RuntimeException("Address not found: " + address);
        }

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
        boolean exists = medicalRecordList.stream().anyMatch(m ->
                m.getFirstName().equals(firstName) &&
                m.getLastName().equals(lastName)
        );

        if (!exists) {
            throw new RuntimeException("Person not found: " + firstName + " " + lastName);
        }

        medicalRecordList.forEach(m -> {
            if (Objects.equals(m.getFirstName(), firstName) && Objects.equals(m.getLastName(), lastName)) {
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
        int index = IntStream.range(0, personList.size())
                .filter(i -> personList.get(i).getFirstName().equals(firstName) && personList.get(i).getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Person not found: " + firstName + " " + lastName));

        personList.remove(index);

        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }

    public void deleteFireStation(String address) {
        int index = IntStream.range(0, fireStationList.size())
                .filter(i -> fireStationList.get(i).getAddress().equals(address))  // ← added closing )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Person not found: " + address));

        fireStationList.remove(index);

        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        int index = IntStream.range(0, medicalRecordList.size())
                .filter(i -> medicalRecordList.get(i).getFirstName().equals(firstName) && personList.get(i).getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Medical Record not found: " + firstName + " " + lastName));

        medicalRecordList.remove(index);

        try {
            saveToFile();
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to persist data to file", e);
        }
    }
} 