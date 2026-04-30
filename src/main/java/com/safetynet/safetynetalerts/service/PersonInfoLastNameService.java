package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MedRecDTO;
import com.safetynet.safetynetalerts.dto.PersonInfoLastNameResponseDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
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
    public PersonInfoLastNameService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<PersonInfoLastNameResponseDTO> GetPersonByLastName(String LastName) {
        List<PersonInfoLastNameResponseDTO> response = new ArrayList<>();

        List<Person> persons = this.dataRepository.getPersonList();
        List<MedicalRecord> medicalRecords = this.dataRepository.getMedicalRecordList();

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        persons = persons.stream().filter(p -> Objects.equals(p.getLastName(), LastName)).toList();
        persons.forEach(p -> {
            PersonInfoLastNameResponseDTO personInfo = new PersonInfoLastNameResponseDTO();
            MedRecDTO medRec = new MedRecDTO();

            MedicalRecord medicalRecord = medicalRecords.stream()
                    .filter(m -> Objects.equals(m.getFirstName(), p.getFirstName()) && Objects.equals(m.getLastName(), p.getLastName()))
                    .toList()
                    .get(0);

            medRec.setMedications(medicalRecord.getMedications());
            medRec.setAllergies(medicalRecord.getAllergies());

            personInfo.setFirstName(p.getFirstName());
            personInfo.setLastName(p.getLastName());
            personInfo.setAddress(p.getAddress());
            personInfo.setAge(Period.between(LocalDate.parse(medicalRecord.getBirthdate(), formatter), now).getYears());
            personInfo.setEmail(p.getEmail());
            personInfo.setMedicalRecord(medRec);

            response.add(personInfo);
        });

        return response;
    }
}
