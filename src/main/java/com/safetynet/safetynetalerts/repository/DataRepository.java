package com.safetynet.safetynetalerts.repository;

import java.io.IOException;
import java.util.List;
import java.io.File;

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

    public DataRepository() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new File("src/main/resources/data.json"));

        this.personList = mapper.convertValue(json.get("persons"), new TypeReference<>() {});
        this.fireStationList = mapper.convertValue(json.get("firestations"), new TypeReference<>() {});
        this.medicalRecordList = mapper.convertValue(json.get("medicalrecords"), new TypeReference<>() {});
    }

    public List<Person> getPersonList() { return personList; }
    public List<Firestation> getFireStationList() { return fireStationList; }
    public List<MedicalRecord> getMedicalRecordList() { return medicalRecordList; }
}