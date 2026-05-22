package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
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
        // On essaye d'enregistrer le dossier médical avec le dataRepository
        try {
            logger.debug("Saving medical record to list...");
            dataRepository.addMedicalRecordToList(medicalRecord);
            logger.debug("Saved !!!");
            return true;
        }

        // S'il y a une erreur, on renvoie false
        catch (Exception e) {
            logger.error("Error while saving medical record");
            return false;
        }
    }

    public MedicalRecord update(String firstName, String lastName, MedicalUpdateDTO dto) {
        // On essaye de modifier le dossier médical avec le dataRepository
        try {
            logger.debug("Updating medical record with first name '{}' and last name '{}'", firstName, lastName);
            dataRepository.updateMedicalRecord(firstName, lastName, dto);
            MedicalRecord updatedMedicalRecord = dataRepository.getMedicalRecordList().stream()
                    .filter(m -> Objects.equals(firstName, m.getFirstName()))
                    .filter(m -> Objects.equals(lastName, m.getLastName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Person not found after update: " + firstName + " " + lastName));

            logger.debug("Updated !!!");
            return updatedMedicalRecord;
        }

        // S'il y a une erreur, on renvoie une Runtime Exception
        catch (Exception e) {
            logger.error("Error while updating medical record");
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String firstName, String lastName) {
        // On essaye de supprimer le dossier médical avec le dataRepository
        try {
            logger.debug("Deleting medical record with first name '{}' and last name '{}'", firstName, lastName);
            dataRepository.deleteMedicalRecord(firstName, lastName);

            logger.debug("Deleted !!!");
            return true;
        }

        // S'il y a une erreur, on renvoie une Runtime Exception
        catch (Exception e) {
            logger.error("Error while deleting medical record");
            throw new RuntimeException(e);
        }
    }
}