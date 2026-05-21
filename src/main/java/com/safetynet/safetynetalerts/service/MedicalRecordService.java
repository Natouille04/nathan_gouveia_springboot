package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MedicalRecordService {
    private DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

    public MedicalRecordService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public boolean save(MedicalRecord medicalRecord) {
        try {
            logger.debug("Saving medical record to list...");
            dataRepository.addMedicalRecordToList(medicalRecord);
            logger.debug("Saved !!!");
            return true;
        }

        catch (Exception e) {
            logger.error("Error while saving medical record");
            return false;
        }
    }

    public MedicalRecord update(String firstName, String lastName, MedicalUpdateDTO dto) {
        try {
            logger.debug("Updating fire station with first name '{}' and last name '{}'", firstName, lastName);
            dataRepository.updateMedicalRecord(firstName, lastName, dto);
            MedicalRecord updatedMedicalRecord = dataRepository.getMedicalRecordList().stream()
                    .filter(m -> Objects.equals(firstName, m.getFirstName()))
                    .filter(m -> Objects.equals(lastName, m.getLastName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Person not found after update: " + firstName + " " + lastName));

            logger.debug("Updated !!!");
            return updatedMedicalRecord;
        }

        catch (Exception e) {
            logger.error("Error while updating fire station");
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String firstName, String lastName) {
        try {
            logger.debug("Deleting fire station with first name '{}' and last name '{}'", firstName, lastName);
            dataRepository.deleteMedicalRecord(firstName, lastName);

            logger.debug("Deleted !!!");
            return true;
        }

        catch (Exception e) {
            logger.error("Error while deleting fire station");
            throw new RuntimeException(e);
        }
    }
}
