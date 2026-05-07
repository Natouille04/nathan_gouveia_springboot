package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MedicalRecordService {
    private DataRepository dataRepository;

    public MedicalRecordService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public boolean save(MedicalRecord medicalRecord) {
        try {
            dataRepository.addMedicalRecordToList(medicalRecord);
            return true;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MedicalRecord update(String firstName, String lastName, MedicalUpdateDTO dto) {
        try {
            dataRepository.updateMedicalRecord(firstName, lastName, dto);
            MedicalRecord updatedMedicalRecord = dataRepository.getMedicalRecordList().stream()
                    .filter(m -> Objects.equals(firstName, m.getFirstName()) && Objects.equals(lastName, m.getLastName()))
                    .toList()
                    .get(0);

            return updatedMedicalRecord;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String firstName, String lastName) {
        try {
            dataRepository.deleteMedicalRecord(firstName, lastName);
            return true;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
