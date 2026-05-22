package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;
    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    // Endpoint POST /medicalRecord : Ajoute un dossier médical selon les informations données dans le corps de la requète

    @PostMapping("/medicalRecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord newMedicalRecord) {
        logger.info("POST medicalRecord called with RequestBody");

        if (medicalRecordService.save(newMedicalRecord)) {
            return ResponseEntity.ok().build();
        }

        logger.error("POST ERROR medicalRecord : Conflict with already existing data");
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // Endpoint PATCH /medicalRecord : Modifie un dossier médical selon les informations données dans le corps de la requète

    @PatchMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestBody MedicalUpdateDTO dto) {
        try {
            logger.info("PATCH medicalRecord called with param : {}, {}, and Request body", firstName, lastName);
            MedicalRecord updated = medicalRecordService.update(firstName, lastName, dto);
            return ResponseEntity.ok(updated);
        }

        catch (RuntimeException e) {
            logger.error("PATCH ERROR medicalRecord : firstName '{}' and/or lastName '{}' not found", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint DELETE /medicalRecord : Supprime un dossier médical selon les informations données dans le paramètre de la requète

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        try {
            logger.info("DELETE medicalRecord called with param : {}, {}, and Request body", firstName, lastName);
            medicalRecordService.delete(firstName, lastName);
            return ResponseEntity.ok().build();
        }

        catch(RuntimeException e) {
            logger.error("DELETE ERROR medicalRecord : firstName '{}' and/or lastName '{}' not found", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
