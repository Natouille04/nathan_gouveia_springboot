package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FireStationsService {
    private DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(FireStationsService.class);

    public FireStationsService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public boolean save(Firestation firestation) {
        // On essaye d'enregistrer la station avec le dataRepository
        try {
            logger.debug("Saving fire station to list...");
            dataRepository.addFirestationToList(firestation);
            logger.debug("Saved !!!");
            return true;
        }

        // Si il y'a une erreur, on revoie false
        catch (Exception e) {
            logger.error("Error while saving fire station");
            return false;
        }
    }

    public Firestation update(String address, FireStationUpdateDTO firestation) {
        // On essaye de modifier la station avec le dataRepository
        try {
            logger.debug("Updating fire station with address '{}'", address);
            dataRepository.updateFireStation(address, firestation);
            Firestation updatedFirestation = dataRepository.getFireStationList().stream()
                    .filter(f -> Objects.equals(firestation.getAddress(), f.getAddress()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Fire Station not found after update: " + firestation));

            logger.debug("Updated !!!");
            return updatedFirestation;
        }

        // S'il y a une erreur, on renvoie une Runtime Exception
        catch (Exception e) {
            logger.error("Error while updating fire station");
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String address) {
        try {
            logger.debug("Deleting fire station with address '{}'", address);
            dataRepository.deleteFireStation(address);

            logger.debug("Deleted !!!");
            return true;
        }

        // S'il y a une erreur, on renvoie une Runtime Exception
        catch (Exception e) {
            logger.error("Error while deleting fire station");
            throw new RuntimeException(e);
        }
    }
}
