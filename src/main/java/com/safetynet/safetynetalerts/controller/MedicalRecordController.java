package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MedicalUpdateDTO;
import com.safetynet.safetynetalerts.dto.PersonUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/medicalrecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord newMedicalRecord) {
        if (medicalRecordService.save(newMedicalRecord)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/medicalrecord/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalUpdateDTO dto) {
        try {
            MedicalRecord updated = medicalRecordService.update(firstName, lastName, dto);
            return ResponseEntity.ok(updated);
        }

        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName
    ) {
        if(medicalRecordService.delete(firstName, lastName)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
