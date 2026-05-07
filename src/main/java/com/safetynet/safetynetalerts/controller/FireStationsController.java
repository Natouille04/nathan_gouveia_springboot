package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FireStationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationsController {
    private final FireStationsService fireStationsService;

    public FireStationsController(FireStationsService fireStationsService) {
        this.fireStationsService = fireStationsService;
    }

    @PostMapping("/firestation")
    public ResponseEntity<Void> addFireStation(@RequestBody Firestation newfirestation) {
        if (fireStationsService.save(newfirestation)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping("/firestation")
    public ResponseEntity<Firestation> updateFireStation(
            @RequestParam String address,
            @RequestBody FireStationUpdateDTO firestation
    ) {
        try {
            Firestation updated = fireStationsService.update(address, firestation);
            return ResponseEntity.ok(updated);
        }

        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFireStation(
            @RequestParam String address
    ) {
        if (fireStationsService.delete(address)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
