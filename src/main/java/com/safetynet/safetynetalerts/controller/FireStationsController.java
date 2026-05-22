package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FireStationsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationsController {
    private final FireStationsService fireStationsService;
    private static final Logger logger = LogManager.getLogger(FireStationsController.class);

    public FireStationsController(FireStationsService fireStationsService) {
        this.fireStationsService = fireStationsService;
    }

    // Endpoint POST /fireStation : Ajoute une station de pompier selon les informations donnée dans le corps de la requète

    @PostMapping("/fireStation")
    public ResponseEntity<Void> addFireStation(@RequestBody Firestation newfirestation) {
        logger.info("POST fireStation called with Request body");

        if (fireStationsService.save(newfirestation)) {
            return ResponseEntity.ok().build();
        }

        logger.error("POST ERROR fireStation : Conflict with already existing data");
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // Endpoint PATCH /fireStation : Modifie une station de pompier selon les informations donnée dans le corps de la requète

    @PatchMapping("/fireStation")
    public ResponseEntity<Firestation> updateFireStation(
            @RequestParam String address,
            @RequestBody FireStationUpdateDTO firestation
    ) {
        try {
            logger.info("PATCH fireStation called with param : {}, and Request body", address);
            Firestation updated = fireStationsService.update(address, firestation);
            return ResponseEntity.ok(updated);
        }

        catch (RuntimeException e) {
            logger.error("PATCH ERROR fireStation : Address '{}' not found", address);
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint DELETE /fireStation : Supprime une station de pompier selon les informations donnée dans le parametre de la requète

    @DeleteMapping("/fireStation")
    public ResponseEntity<Void> deleteFireStation(@RequestParam String address) {
        try {
            logger.info("DELETE fireStation called with param : {}", address);
            fireStationsService.delete(address);
            return ResponseEntity.ok().build();
        }

        catch(RuntimeException e) {
            logger.error("DELETE ERROR fireStation : Address '{}' not found", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
